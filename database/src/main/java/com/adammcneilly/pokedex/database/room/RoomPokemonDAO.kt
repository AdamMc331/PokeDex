package com.adammcneilly.pokedex.database.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.adammcneilly.pokedex.database.models.PersistablePokemon
import kotlinx.coroutines.flow.Flow

@Dao
internal interface RoomPokemonDAO {
    @Insert
    suspend fun insert(pokemon: PersistablePokemon): Long

    @Insert
    suspend fun insertAll(pokemon: List<PersistablePokemon>): List<Long>

    @Query("DELETE FROM PersistablePokemon")
    suspend fun deleteAllPokemon(): Int

    @Query("SELECT * FROM PersistablePokemon WHERE name = :name")
    suspend fun getPokemonByName(name: String): PersistablePokemon?

    @Query("SELECT * FROM PersistablePokemon WHERE name = :name")
    fun getPokemonByNameFlow(name: String): Flow<PersistablePokemon?>

    @Query("SELECT * FROM PersistablePokemon")
    suspend fun getAllPokemon(): List<PersistablePokemon>
}