package com.adammcneilly.pokedex.data

import com.adammcneilly.pokedex.data.remote.PokemonAPI
import com.adammcneilly.pokedex.database.PokedexDatabase
import com.adammcneilly.pokedex.models.Pokemon
import com.adammcneilly.pokedex.models.PokemonResponse
import com.adammcneilly.pokedex.models.toPokemon

/**
 * Implementation of a [PokemonRepository] that fetch from both local and remote sources.
 */
open class PokemonService(
    private val database: PokedexDatabase,
    private val api: PokemonAPI
) : PokemonRepository {
    override suspend fun getPokemon(): PokemonResponse {
        return api.getPokemonAsync().await()
    }

    override suspend fun getPokemonDetail(pokemonName: String): Pokemon {
        return getPokemonDetailFromDatabase(pokemonName) ?: getPokemonDetailFromNetwork(pokemonName)
    }

    private suspend fun getPokemonDetailFromDatabase(pokemonName: String): Pokemon? {
        return database.getPokemonByName(pokemonName).toPokemon()
    }

    private suspend fun getPokemonDetailFromNetwork(pokemonName: String): Pokemon {
        return api.getPokemonDetailAsync(pokemonName).await().also {
            database.insertPokemon(it.toPersistablePokemon())
        }
    }
}