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

    /**
     * At this point, we only want to request the pokemon list from the server. In the
     * future we should store and request that list from the database as well.
     */
    override suspend fun getPokemon(): PokemonResponse? {
        return api.getPokemon()
    }

    override fun getPokemonDetail(pokemonName: String): Flow<Result<Pokemon>> {
        return flow {
            val pokemonResult = getPokemonDetailFromDatabase(pokemonName)
                ?: getPokemonDetailFromNetwork(pokemonName)

            emit(Result.success(pokemonResult))
        }.catch { exception ->
            emit(Result.failure(exception))
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