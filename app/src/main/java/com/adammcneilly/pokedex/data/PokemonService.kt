package com.adammcneilly.pokedex.data

import com.adammcneilly.pokedex.database.PokedexDatabase
import com.adammcneilly.pokedex.database.models.PersistablePokemon
import com.adammcneilly.pokedex.models.Pokemon
import com.adammcneilly.pokedex.models.PokemonResponse
import com.adammcneilly.pokedex.models.toPokemon
import com.adammcneilly.pokedex.models.toPokemonResponse
import com.adammcneilly.pokedex.network.PokemonAPI

/**
 * Implementation of a [PokemonRepository] that fetch from both local and remote sources.
 */
open class PokemonService(
    private val database: PokedexDatabase?,
    private val api: PokemonAPI?
) : PokemonRepository {
    override suspend fun getPokemon(): PokemonResponse? {
        val dbPokemon = database?.getAllPokemon()

        return if (dbPokemon?.isNotEmpty() == true) {
            PokemonResponse(results = dbPokemon.mapNotNull(PersistablePokemon::toPokemon))
        } else {
            api?.getPokemon()?.toPokemonResponse()?.also { pokemonResponse ->
                val pokemonList = pokemonResponse.results
                    ?.map(Pokemon::toPersistablePokemon)
                    .orEmpty()

                database?.insertAllPokemon(pokemonList)
            }
        }
    }

    override suspend fun getPokemonDetail(pokemonName: String): Pokemon? {
        return getPokemonDetailFromDatabase(pokemonName) ?: getPokemonDetailFromNetwork(pokemonName)
    }

    private suspend fun getPokemonDetailFromDatabase(pokemonName: String): Pokemon? {
        return database?.getPokemonByName(pokemonName)?.toPokemon()
    }

    private suspend fun getPokemonDetailFromNetwork(pokemonName: String): Pokemon {
        return api?.getPokemonDetail(pokemonName).toPokemon()?.also {
            database?.insertPokemon(it.toPersistablePokemon())
        } ?: Pokemon()
    }
}