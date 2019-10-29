package com.adammcneilly.pokedex.database.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.adammcneilly.pokedex.database.models.PersistablePokemon

@Database(entities = [(PersistablePokemon::class)], version = 1)
@TypeConverters((TypeSlotListConverter::class))
abstract class RoomPokedexDatabase : RoomDatabase() {
    abstract fun pokemonDao(): RoomPokemonDAO

    companion object {
        private var INSTANCE: RoomPokedexDatabase? = null

        fun getInMemoryDatabase(context: Context): RoomPokedexDatabase {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(
                    context,
                    RoomPokedexDatabase::class.java, "pokedex.db"
                )
                    .build()
            }

            return INSTANCE!!
        }
    }
}