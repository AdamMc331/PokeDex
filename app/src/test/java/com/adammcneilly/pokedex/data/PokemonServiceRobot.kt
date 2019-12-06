package com.adammcneilly.pokedex.data

import com.adammcneilly.pokedex.database.PokedexDatabase
import com.adammcneilly.pokedex.database.models.PersistablePokemon
import com.adammcneilly.pokedex.models.Pokemon
import com.adammcneilly.pokedex.models.PokemonResponse
import com.adammcneilly.pokedex.network.PokemonAPI
import com.adammcneilly.pokedex.network.models.PokemonDTO
import com.adammcneilly.pokedex.network.models.PokemonResponseDTO
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals

class PokemonServiceRobot {
    private val mockDatabase = mockk<PokedexDatabase>()
    private val mockAPI = mockk<PokemonAPI>()
    private val service = PokemonService(mockDatabase, mockAPI)

    fun mockNetworkPokemon(response: PokemonResponseDTO) = apply {
        coEvery { mockAPI.getPokemon() } returns response
    }

    fun mockNetworkPokemonDetailForPokemon(pokemonName: String, detail: PokemonDTO) = apply {
        coEvery { mockAPI.getPokemonDetail(pokemonName) } returns detail
    }

    fun mockLocalPokemonDetailForPokemon(pokemonName: String, detail: PersistablePokemon) = apply {
        coEvery { mockDatabase.getPokemonByName(pokemonName) } returns detail
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