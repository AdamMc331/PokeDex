package com.adammcneilly.pokedex.data

import com.adammcneilly.pokedex.DispatcherProvider
import com.adammcneilly.pokedex.core.Pokemon
import com.adammcneilly.pokedex.core.PokemonResponse
import com.adammcneilly.pokedex.database.PokedexDatabase
import com.adammcneilly.pokedex.network.PokemonAPI
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

/**
 * Implementation of a [PokemonRepository] that fetch from both local and remote sources.
 */
open class PokemonService(
    private val database: PokedexDatabase?,
    private val api: PokemonAPI,
    private val dispatcherProvider: DispatcherProvider = DispatcherProvider()
) : PokemonRepository {

    override fun getPokemon(): Flow<PokemonResponse> {
        return flow {
            val pokemonResponse = api.getPokemon()
            emit(pokemonResponse)
            // emit(Result.success(pokemonResponse))
        }.catch {
            // emit(Result.failure(exception))
        }
            .flowOn(dispatcherProvider.IO)
    }

    override fun getPokemonDetail(pokemonName: String): Flow<Pokemon> {
        return flow {
            val pokemonResult = getPokemonDetailFromDatabase(pokemonName)
                ?: getPokemonDetailFromNetwork(pokemonName)

            emit(pokemonResult)
            // emit(Result.success(pokemonResult))
        }.catch {
            // emit(Result.failure(exception))
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