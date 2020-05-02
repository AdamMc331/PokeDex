package com.adammcneilly.pokedex.database.room

import androidx.room.testing.MigrationTestHelper
import androidx.sqlite.db.framework.FrameworkSQLiteOpenHelperFactory
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test

class RoomPokedexMigrationsTest {
    private val TEST_DB = "migration-test"

    @JvmField
    @Rule
    val migrationTestHelper = MigrationTestHelper(
        InstrumentationRegistry.getInstrumentation(),
        RoomPokedexDatabase::class.java.canonicalName,
        FrameworkSQLiteOpenHelperFactory()
    )

    @Test
    fun migrate1to2() {
        var database = migrationTestHelper.createDatabase(TEST_DB, 1).apply {
            execSQL(
                """
                INSERT INTO PersistablePokemon VALUES ('bulbasaur', 'testImage', 0, 1) 
                """.trimIndent()
            )

            close()
        }

        database = migrationTestHelper.runMigrationsAndValidate(TEST_DB, 2, true, MIGRATION_1_2)

        val resultCursor = database.query("SELECT * FROM PersistablePokemon")

        // let's make sure we can find our "pokedexNumber" and it's equal to the default value
        // after migration
        assertTrue(resultCursor.moveToFirst())

        val columnIndex = resultCursor.getColumnIndex("pokedexNumber")
        val numberFromDatabase = resultCursor.getString(columnIndex)
        assertEquals("", numberFromDatabase)
    }
}