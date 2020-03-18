package com.adammcneilly.pokedex.database

import android.content.Context
import com.adammcneilly.pokedex.core.Pokemon
import com.adammcneilly.pokedex.database.models.PersistablePokemon
import com.adammcneilly.pokedex.database.room.RoomPokedexDatabase

@Suppress("UNUSED_PARAMETER")
abstract class PokedexDatabase(context: Context) {
    abstract suspend fun insertPokemon(pokemon: Pokemon): Long
    abstract suspend fun insertAllPokemon(pokemon: List<Pokemon>): List<Long>
    abstract suspend fun getPokemonByName(name: String): Pokemon?
    abstract suspend fun getAllPokemon(): List<Pokemon>?
}

class DefaultPokedexDatabase(context: Context) : PokedexDatabase(context) {
    private val roomDatabase = RoomPokedexDatabase.getInMemoryDatabase(context)

    override suspend fun insertPokemon(pokemon: Pokemon): Long {
        val persistablePokemon = PersistablePokemon.fromPokemon(pokemon)
        return roomDatabase.pokemonDao().insert(persistablePokemon)
    }

    override suspend fun insertAllPokemon(pokemon: List<Pokemon>): List<Long> {
        val persistablePokemon = pokemon.map(PersistablePokemon.Companion::fromPokemon)
        return roomDatabase.pokemonDao().insertAll(persistablePokemon)
    }

    override suspend fun getPokemonByName(name: String): Pokemon? {
        return roomDatabase.pokemonDao().getPokemonByName(name)?.toPokemon()
    }

    override suspend fun getAllPokemon(): List<Pokemon>? {
        return roomDatabase.pokemonDao().getAllPokemon().map(PersistablePokemon::toPokemon)
    }
}