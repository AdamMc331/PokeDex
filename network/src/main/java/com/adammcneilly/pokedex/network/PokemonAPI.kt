package com.adammcneilly.pokedex.network

import com.adammcneilly.pokedex.core.Pokemon
import com.adammcneilly.pokedex.core.PokemonResponse
import com.adammcneilly.pokedex.network.retrofit.RetrofitPokemonAPI

abstract class PokemonAPI(baseUrl: String) {
    abstract suspend fun getPokemon(): PokemonResponse
    abstract suspend fun getPokemonDetail(pokemonName: String): Pokemon
}

class DefaultPokemonAPI(baseUrl: String) : PokemonAPI(baseUrl) {
    private val retrofitAPI = RetrofitPokemonAPI.defaultInstance(baseUrl)

    override suspend fun getPokemon(): PokemonResponse {
        return retrofitAPI.getPokemonAsync().await().toPokemonResponse()
    }

    override suspend fun getPokemonDetail(pokemonName: String): Pokemon {
        return retrofitAPI.getPokemonDetailAsync(pokemonName).await().toPokemon()
    }
}