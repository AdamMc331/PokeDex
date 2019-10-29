package com.adammcneilly.pokedex.database.room

import androidx.room.Room
import androidx.test.platform.app.InstrumentationRegistry
import com.adammcneilly.pokedex.database.models.PersistablePokemon
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class RoomPokedexDatabaseTest {
    private lateinit var database: RoomPokedexDatabase
    private lateinit var pokemonDao: RoomPokemonDAO

    @Before
    fun setUp() {
        val context = InstrumentationRegistry.getInstrumentation().context
        database = Room.inMemoryDatabaseBuilder(context, RoomPokedexDatabase::class.java)
            .allowMainThreadQueries().build()
        pokemonDao = database.pokemonDao()
    }

    @After
    fun tearDown() {
        runBlocking {
            pokemonDao.deleteAllPokemon()
            database.close()
        }
    }

    @Test
    fun insertReadPokemon() {
        runBlocking {
            val testName = "Adam"
            val testPokemon = PersistablePokemon(name = testName)
            pokemonDao.insert(testPokemon)

            val result = pokemonDao.getPokemonByName(testName)

            assertEquals(testPokemon, result)
        }
    }
}