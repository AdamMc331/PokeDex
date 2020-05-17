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
    private val mockDatabase = FakeDatabase()
    private val mockAPI = mock(PokemonAPI::class.java)
    private val service = PokemonService(mockDatabase, mockAPI)

    fun mockDatabasePokemon(response: List<Pokemon>?) = apply {
        mockDatabase.mockPokemonList(response)
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

    fun mockLocalPokemonDetailForPokemon(detail: Pokemon) = apply {
        mockDatabase.mockPokemon(detail)
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

private class FakeDatabase : PokedexDatabase {
    private var pokemonList: List<Pokemon>? = null

    override suspend fun insertPokemon(pokemon: Pokemon): Long {
        this.pokemonList = listOf(pokemon)
        return 0
    }

    override suspend fun insertAllPokemon(pokemon: List<Pokemon>): List<Long> {
        TODO("The function insertAllPokemon should not be called for this test case.")
    }

    override suspend fun getPokemonByName(name: String): Pokemon? {
        return pokemonList?.find { pokemon ->
            pokemon.name == name
        }
    }

    override suspend fun getAllPokemon(): List<Pokemon>? {
        return pokemonList
    }

    fun mockPokemonList(pokemonList: List<Pokemon>?) {
        this.pokemonList = pokemonList
    }

    fun mockPokemon(pokemon: Pokemon) {
        this.pokemonList = listOf(pokemon)
    }
}