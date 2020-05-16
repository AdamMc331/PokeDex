package com.adammcneilly.pokedex.di

import android.content.Context
import com.adammcneilly.pokedex.database.PokedexDatabase
import com.adammcneilly.pokedex.database.RoomDatabase

/**
 * This dependency injection graph maintains any relevant dependencies for requesting data from
 * a local storage on the device.
 */
interface LocalStorageGraph {
    val database: PokedexDatabase
}

/**
 * This implementation provides dependencies for using a SQLite database for on-device storage.
 * Currently this only supports the Room library, but we have this central location if we ever add
 * SQLite or an alternative.
 */
class SQLiteDatabaseGraph(
    context: Context
) : LocalStorageGraph {
    override val database: PokedexDatabase = RoomDatabase(context)
}