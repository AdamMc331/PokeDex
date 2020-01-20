package com.adammcneilly.pokedex.database.room

import android.database.sqlite.SQLiteConstraintException
import androidx.room.Room
import androidx.test.platform.app.InstrumentationRegistry
import com.adammcneilly.pokedex.database.models.PersistablePokemon
import java.lang.Exception
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.fail
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

    @Test
    fun insertReadPokemonList() {
        runBlocking {
            val testName = "Adam"
            val testPokemon = PersistablePokemon(name = testName)

            val testName2 = "Mauker"
            val testPokemon2 = PersistablePokemon(name = testName2)
            val testList = listOf(testPokemon, testPokemon2)
            pokemonDao.insertAll(testList)

            val result = pokemonDao.getAllPokemon()

            assertEquals(testList, result)
        }
    }

    @Test
    fun uniqueNameConstraint() {
        runBlocking {
            val testName = "Adam"
            val testPokemon = PersistablePokemon(name = testName)
            val testList = listOf(testPokemon, testPokemon)

            try {
                pokemonDao.insertAll(testList)
                fail("Expected a SQLiteConstraintException")
            } catch (constraint: SQLiteConstraintException) {
                // TODO: Expected
            } catch (e: Exception) {
                fail("Expected a SQLiteConstraintException")
            }
        }
    }
}