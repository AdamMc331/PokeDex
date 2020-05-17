package com.adammcneilly.pokedex.data

import com.adammcneilly.pokedex.core.Pokemon
import com.adammcneilly.pokedex.core.PokemonResponse
import org.junit.Before
import org.junit.Test

class PokemonServiceTest {
    private lateinit var testRobot: PokemonServiceRobot

    @Before
    fun setUp() {
        testRobot = PokemonServiceRobot()
    }

    @Test
    fun `get all Pokemon from Network without DB`() {
        val testPokemon = listOf(Pokemon(name = "Adam"))
        val testResponse = PokemonResponse(results = testPokemon)

        testRobot
            .mockDatabasePokemon(null)
            .mockNetworkPokemon(testResponse)
            .assertPokemonResponse(testResponse)
    }

    @Test
    fun `get all Pokemon from Network with empty  DB`() {
        val testPokemon = listOf(Pokemon(name = "Adam"))
        val testResponse = PokemonResponse(results = testPokemon)

        testRobot
            .mockDatabasePokemon(listOf())
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
    fun `get pokemon detail from Database before API`() {
        val testName = "Adam"
        val networkDetail = Pokemon(name = testName, frontSpriteUrl = "From Network")
        val databaseDetail = Pokemon(name = testName, frontSpriteUrl = "From Database")

        testRobot
            .mockNetworkPokemonDetailForPokemon(testName, networkDetail)
            .mockLocalPokemonDetailForPokemon(databaseDetail)
            .assertPokemonDetail(testName, databaseDetail)
    }
}