package com.adammcneilly.pokedex.data

import com.adammcneilly.pokedex.data.remote.PokemonAPI
import com.adammcneilly.pokedex.database.PokedexDatabase
import com.adammcneilly.pokedex.database.models.PersistablePokemon
import com.adammcneilly.pokedex.models.Pokemon
import com.adammcneilly.pokedex.models.PokemonResponse
import com.adammcneilly.pokedex.toDeferred
import com.adammcneilly.pokedex.whenever
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.mockito.Mockito.mock

class PokemonServiceRobot {
    private val mockDatabase = mock(PokedexDatabase::class.java)
    private val mockAPI = mock(PokemonAPI::class.java)
    private val service = PokemonService(mockDatabase, mockAPI)

    fun mockNetworkPokemon(response: PokemonResponse) = apply {
        whenever(mockAPI.getPokemonAsync()).thenReturn(response.toDeferred())
    }

    fun mockNetworkPokemonDetailForPokemon(pokemonName: String, detail: Pokemon) = apply {
        whenever(mockAPI.getPokemonDetailAsync(pokemonName)).thenReturn(detail.toDeferred())
    }

    fun mockLocalPokemonDetailForPokemon(pokemonName: String, detail: PersistablePokemon) = apply {
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