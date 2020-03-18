package com.adammcneilly.pokedex.database

import android.content.Context
import com.adammcneilly.pokedex.core.Pokemon
import com.adammcneilly.pokedex.database.models.PersistablePokemon
import com.adammcneilly.pokedex.database.room.RoomPokedexDatabase

interface PokedexDatabase {
    suspend fun insertPokemon(pokemon: Pokemon): Long
    suspend fun insertAllPokemon(pokemon: List<Pokemon>): List<Long>
    suspend fun getPokemonByName(name: String): Pokemon?
    suspend fun getAllPokemon(): List<Pokemon>?
}

class RoomDatabase(context: Context) : PokedexDatabase {
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