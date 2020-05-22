package com.adammcneilly.pokedex.data

import com.adammcneilly.pokedex.DispatcherProvider
import com.adammcneilly.pokedex.core.Pokemon
import com.adammcneilly.pokedex.core.PokemonResponse
import com.adammcneilly.pokedex.database.PokedexDatabase
import com.adammcneilly.pokedex.detail.PokemonDetailAction
import com.adammcneilly.pokedex.network.PokemonAPI
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onStart

/**
 * Implementation of a [PokemonRepository] that fetch from both local and remote sources.
 */
open class PokemonService(
    private val database: PokedexDatabase?,
    private val api: PokemonAPI,
    private val dispatcherProvider: DispatcherProvider = DispatcherProvider()
) : PokemonRepository {

    override fun getPokemon(): Flow<Result<PokemonResponse>> {
        return flow {
            val pokemonResponse = api.getPokemon()
            emit(Result.success(pokemonResponse))
        }.catch { exception ->
            emit(Result.failure(exception))
        }
            .flowOn(dispatcherProvider.IO)
    }

    @ExperimentalCoroutinesApi
    override fun getPokemonDetail(pokemonName: String): Flow<PokemonDetailAction> {
        return flow<PokemonDetailAction> {
            val pokemonResult = getPokemonDetailFromDatabase(pokemonName)
                ?: getPokemonDetailFromNetwork(pokemonName)

            emit(PokemonDetailAction.Loaded(pokemonResult))
        }.catch { exception ->
            emit(PokemonDetailAction.Error("TODO"))
        }.onStart {
            emit(PokemonDetailAction.Loading)
        }
            .flowOn(dispatcherProvider.IO)
    }

    private suspend fun getPokemonDetailFromDatabase(pokemonName: String): Pokemon? {
        return database?.getPokemonByName(pokemonName)
    }

    private suspend fun getPokemonDetailFromNetwork(pokemonName: String): Pokemon {
        return api.getPokemonDetail(pokemonName).also {
            database?.insertPokemon(it)
        }
    }
}