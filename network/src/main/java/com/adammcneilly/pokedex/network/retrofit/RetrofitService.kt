package com.adammcneilly.pokedex.network.retrofit

import com.adammcneilly.pokedex.core.Pokemon
import com.adammcneilly.pokedex.core.PokemonResponse
import com.adammcneilly.pokedex.network.PokemonAPI

class RetrofitService(baseUrl: String) : PokemonAPI {
    private val retrofitAPI = RetrofitPokemonAPI.defaultInstance(baseUrl)

    override suspend fun getPokemon(): PokemonResponse {
        return retrofitAPI.getPokemonAsync().await().toPokemonResponse()
    }

    override suspend fun getPokemonDetail(pokemonName: String): Pokemon {
        return retrofitAPI.getPokemonDetailAsync(pokemonName).await().toPokemon()
    }
}