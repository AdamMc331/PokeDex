package com.adammcneilly.pokedex.data

import com.adammcneilly.pokedex.DispatcherProvider
import com.adammcneilly.pokedex.core.Pokemon
import com.adammcneilly.pokedex.core.PokemonResponse
import com.adammcneilly.pokedex.database.PokedexDatabase
import com.adammcneilly.pokedex.network.PokemonAPI
import com.dropbox.android.external.store4.SourceOfTruth
import com.dropbox.android.external.store4.StoreBuilder
import com.dropbox.android.external.store4.StoreRequest
import com.dropbox.android.external.store4.StoreResponse
import com.dropbox.android.external.store4.fetcher
import com.dropbox.android.external.store4.valueFetcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

/**
 * Implementation of a [PokemonRepository] that fetch from both local and remote sources.
 */
open class PokemonService(
    private val database: PokedexDatabase,
    private val api: PokemonAPI,
    private val dispatcherProvider: DispatcherProvider = DispatcherProvider()
) : PokemonRepository {

    private val store = StoreBuilder.from(
        fetcher = valueFetcher<String, Pokemon> { pokemonName ->
            api.getPokemonDetailFlow(pokemonName)
        },
        sourceOfTruth = SourceOfTruth.from(
            reader = database::getPokemonByNameFlow,
            writer = { _, input ->
                database.insertPokemon(input)
            }
        )
    ).build()

    override fun getPokemon(): Flow<Result<PokemonResponse>> {
        return flow {
            val pokemonResponse = api.getPokemon()
            emit(Result.success(pokemonResponse))
        }.catch { exception ->
            emit(Result.failure(exception))
        }
            .flowOn(dispatcherProvider.IO)
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

    override fun getPokemonDetailFromStore(pokemonName: String): Flow<StoreResponse<Pokemon>> {
        val request = StoreRequest.cached(key = pokemonName, refresh = false)
        return store.stream(request)
    }

    private suspend fun getPokemonDetailFromDatabase(pokemonName: String): Pokemon? {
        return database.getPokemonByName(pokemonName)
    }

    private suspend fun getPokemonDetailFromNetwork(pokemonName: String): Pokemon {
        return api.getPokemonDetail(pokemonName).also {
            database.insertPokemon(it)
        }
    }
}