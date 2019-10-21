package com.adammcneilly.pokedex.database.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.adammcneilly.pokedex.database.models.PersistablePokemon

@Dao
interface RoomPokemonDAO {
    @Insert
    suspend fun insert(pokemon: PersistablePokemon): Long

    @Query("DELETE FROM PersistablePokemon")
    suspend fun deleteAllPokemon(): Int

    @Query("SELECT * FROM PersistablePokemon WHERE name = :name")
    suspend fun getPokemonByName(name: String): PersistablePokemon?
}