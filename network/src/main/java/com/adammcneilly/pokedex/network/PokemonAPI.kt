package com.adammcneilly.pokedex.network

import com.adammcneilly.pokedex.core.Pokemon
import com.adammcneilly.pokedex.core.PokemonResponse
import com.adammcneilly.pokedex.network.retrofit.RetrofitPokemonAPI

interface PokemonAPI {
    suspend fun getPokemon(): PokemonResponse
    suspend fun getPokemonDetail(pokemonName: String): Pokemon
}

class RetrofitAPI(baseUrl: String) : PokemonAPI {
    private val retrofitAPI = RetrofitPokemonAPI.defaultInstance(baseUrl)

    override suspend fun getPokemon(): PokemonResponse {
        return retrofitAPI.getPokemonAsync().await().toPokemonResponse()
    }

    override suspend fun getPokemonDetail(pokemonName: String): Pokemon {
        return retrofitAPI.getPokemonDetailAsync(pokemonName).await().toPokemon()
    }
}