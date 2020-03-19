package com.adammcneilly.pokedex.data

import com.adammcneilly.pokedex.core.Pokemon
import com.adammcneilly.pokedex.core.PokemonResponse
import com.adammcneilly.pokedex.database.PokedexDatabase
import com.adammcneilly.pokedex.network.PokemonAPI
import com.adammcneilly.pokedex.whenever
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.mockito.Mockito.mock

class PokemonServiceRobot {
    private val mockDatabase = mock(PokedexDatabase::class.java)
    private val mockAPI = mock(PokemonAPI::class.java)
    private val service = PokemonService(mockDatabase, mockAPI)

    fun mockDatabasePokemon(response: List<Pokemon>?) = apply {
        runBlocking {
            whenever(mockDatabase.getAllPokemon()).thenReturn(response)
        }
    }

    fun mockNetworkPokemon(response: PokemonResponse) = apply {
        runBlocking {
            whenever(mockAPI.getPokemon()).thenReturn(response)
        }
    }

    fun mockNetworkPokemonDetailForPokemon(pokemonName: String, detail: Pokemon) = apply {
        runBlocking {
            whenever(mockAPI.getPokemonDetail(pokemonName)).thenReturn(detail)
        }
    }

    fun mockLocalPokemonDetailForPokemon(pokemonName: String, detail: Pokemon) = apply {
        runBlocking {
            whenever(mockDatabase.getPokemonByName(pokemonName)).thenReturn(detail)
        }
    }

    fun assertPokemonResponse(expectedResponse: PokemonResponse) = apply {
        runBlocking {
            assertEquals(expectedResponse, service.getPokemon())
        }
    }

    fun assertPokemonDetail(pokemonName: String, expectedDetail: Pokemon?) = apply {
        runBlocking {
            assertEquals(expectedDetail, service.getPokemonDetail(pokemonName))
        }
    }
}