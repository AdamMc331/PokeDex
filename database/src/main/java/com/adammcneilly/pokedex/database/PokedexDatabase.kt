package com.adammcneilly.pokedex.database

import android.content.Context
import com.adammcneilly.pokedex.database.models.PersistablePokemon
import com.adammcneilly.pokedex.database.room.RoomPokedexDatabase

@Suppress("UNUSED_PARAMETER")
abstract class PokedexDatabase(context: Context) {
    abstract suspend fun insertPokemon(pokemon: PersistablePokemon): Long
    abstract suspend fun getPokemonByName(name: String): PersistablePokemon?
}

class DefaultPokedexDatabase(context: Context) : PokedexDatabase(context) {
    private val roomDatabase = RoomPokedexDatabase.getInMemoryDatabase(context)

    override suspend fun insertPokemon(pokemon: PersistablePokemon): Long {
        return roomDatabase.pokemonDao().insert(pokemon)
    }

    override suspend fun getPokemonByName(name: String): PersistablePokemon? {
        return roomDatabase.pokemonDao().getPokemonByName(name)
    }
}