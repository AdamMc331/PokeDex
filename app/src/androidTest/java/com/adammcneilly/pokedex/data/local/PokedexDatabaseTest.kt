package com.adammcneilly.pokedex.data.local

import androidx.room.Room
import androidx.test.rule.ActivityTestRule
import com.adammcneilly.pokedex.main.MainActivity
import com.adammcneilly.pokedex.models.Pokemon
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class PokedexDatabaseTest {
    private lateinit var database: PokedexDatabase
    private lateinit var pokemonDao: PokemonDAO

    @JvmField
    @Rule
    val mainActivity = ActivityTestRule<MainActivity>(MainActivity::class.java)

    @Before
    fun setUp() {
        val context = mainActivity.activity
        database = Room.inMemoryDatabaseBuilder(context, PokedexDatabase::class.java).allowMainThreadQueries().build()
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
            val testPokemon = Pokemon(name = testName)

            val newId = pokemonDao.insert(testPokemon)

            val expectedPokemon = testPokemon.copy(_id = newId)
            val result = pokemonDao.getPokemonByName(testName)

            assertEquals(expectedPokemon, result)
        }
    }
}