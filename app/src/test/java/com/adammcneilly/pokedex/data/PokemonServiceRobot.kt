package com.adammcneilly.pokedex.data

import com.adammcneilly.pokedex.core.Pokemon
import com.adammcneilly.pokedex.core.PokemonResponse
import com.adammcneilly.pokedex.database.PokedexDatabase
import com.adammcneilly.pokedex.network.PokemonAPI
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals

class PokemonServiceRobot {
    private val mockDatabase = FakeDatabase()
    private val mockAPI = FakeAPI()
    private val service = PokemonService(mockDatabase, mockAPI)

    fun mockDatabasePokemon(response: List<Pokemon>?) = apply {
        mockDatabase.mockPokemonList(response)
    }

    fun mockNetworkPokemon(response: PokemonResponse) = apply {
        mockAPI.mockPokemonResponse(response)
    }

    fun mockNetworkPokemonDetailForPokemon(detail: Pokemon) = apply {
        mockAPI.mockPokemonDetail(detail)
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
            assertEquals(expectedDetail, service.getPokemonDetail(pokemonName).first().getOrNull())
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

private class FakeAPI : PokemonAPI {
    private var pokemonResponse = PokemonResponse()
    private var pokemonDetail = Pokemon()

    override suspend fun getPokemon(): PokemonResponse {
        return this.pokemonResponse
    }

    override suspend fun getPokemonDetail(pokemonName: String): Pokemon {
        return this.pokemonDetail
    }

    fun mockPokemonResponse(response: PokemonResponse) {
        this.pokemonResponse = response
    }

    fun mockPokemonDetail(detail: Pokemon) {
        this.pokemonDetail = detail
    }
}