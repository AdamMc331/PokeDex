package com.adammcneilly.pokedex.data

import com.adammcneilly.pokedex.database.models.PersistablePokemon
import com.adammcneilly.pokedex.models.toPokemon
import com.adammcneilly.pokedex.models.toPokemonResponse
import com.adammcneilly.pokedex.network.models.PokemonDTO
import com.adammcneilly.pokedex.network.models.PokemonResponseDTO
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
        val testPokemon = listOf(PokemonDTO(name = "Adam"))
        val testResponse = PokemonResponseDTO(results = testPokemon)

        testRobot
            .mockNetworkPokemon(testResponse)
            .assertPokemonResponse(testResponse.toPokemonResponse())
    }

    @Test
    fun `get pokemon detail from API without database`() {
        val testName = "Adam"
        val networkDetail = PokemonDTO(name = "From Network")

        testRobot
            .mockNetworkPokemonDetailForPokemon(testName, networkDetail)
            .assertPokemonDetail(testName, networkDetail.toPokemon())
    }

    @Test
    fun `get pokemon detail from Database Before API`() {
        val testName = "Adam"
        val networkDetail = PokemonDTO(name = "From Network")
        val databaseDetail = PersistablePokemon(name = "From Database")

        testRobot
            .mockNetworkPokemonDetailForPokemon(testName, networkDetail)
            .mockLocalPokemonDetailForPokemon(testName, databaseDetail)
            .assertPokemonDetail(testName, databaseDetail.toPokemon())
    }
}