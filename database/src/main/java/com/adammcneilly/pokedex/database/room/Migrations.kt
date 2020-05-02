package com.adammcneilly.pokedex.database.room

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

internal val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL("ALTER TABLE PersistablePokemon ADD COLUMN pokedexNumber TEXT NOT NULL DEFAULT ''")
    }
}