package com.adammcneilly.pokedex.data

import com.adammcneilly.pokedex.data.local.PokedexDatabase
import com.adammcneilly.pokedex.data.local.PokemonDAO
import com.adammcneilly.pokedex.data.remote.PokemonAPI
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

    // TODO: See if there's a way to avoid this, maybe wrap our database in another layer?
    private val mockPokemonDAO = mock(PokemonDAO::class.java)

    init {
        whenever(mockDatabase.pokemonDao()).thenReturn(mockPokemonDAO)
    }

    fun mockNetworkPokemon(response: PokemonResponse) = apply {
        whenever(mockAPI.getPokemonAsync()).thenReturn(response.toDeferred())
    }

    fun mockNetworkPokemonDetailForPokemon(pokemonName: String, detail: Pokemon) = apply {
        whenever(mockAPI.getPokemonDetailAsync(pokemonName)).thenReturn(detail.toDeferred())
    }

    fun mockLocalPokemonDetailForPokemon(pokemonName: String, detail: Pokemon) = apply {
        runBlocking {
            whenever(mockPokemonDAO.getPokemonByName(pokemonName)).thenReturn(detail)
        }
    }

    fun assertPokemonResponse(expectedResponse: PokemonResponse) = apply {
        runBlocking {
            assertEquals(expectedResponse, service.getPokemon())
        }
    }

    fun assertPokemonDetail(pokemonName: String, expectedDetail: Pokemon) = apply {
        runBlocking {
            assertEquals(expectedDetail, service.getPokemonDetail(pokemonName))
        }
    }
}