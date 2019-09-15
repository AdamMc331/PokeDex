package com.adammcneilly.pokedex.data

import com.adammcneilly.pokedex.models.Pokemon
import com.adammcneilly.pokedex.models.PokemonResponse
import org.junit.Before
import org.junit.Test

class PokemonServiceTest {
    private lateinit var testRobot: PokemonServiceRobot

    @Before
    fun setUp() {
        testRobot = PokemonServiceRobot()
    }

    @Test
    fun getPokemon() {
        val testPokemon = listOf(Pokemon(name = "Adam"))
        val testResponse = PokemonResponse(results = testPokemon)

        testRobot
            .mockNetworkPokemon(testResponse)
            .assertPokemonResponse(testResponse)
    }

    @Test
    fun `get pokemon detail from API without database`() {
        val testName = "Adam"
        val networkDetail = Pokemon(name = "From Network")

        testRobot
            .mockNetworkPokemonDetailForPokemon(testName, networkDetail)
            .assertPokemonDetail(testName, networkDetail)
    }

    @Test
    fun `get pokemon detail from Database Before API`() {
        val testName = "Adam"
        val networkDetail = Pokemon(name = "From Network")
        val databaseDetail = Pokemon(name = "From Database")

        testRobot
            .mockNetworkPokemonDetailForPokemon(testName, networkDetail)
            .mockLocalPokemonDetailForPokemon(testName, databaseDetail)
            .assertPokemonDetail(testName, databaseDetail)
    }
}