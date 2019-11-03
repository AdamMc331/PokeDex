package com.adammcneilly.pokedex.network

import com.adammcneilly.pokedex.network.models.PokemonDTO
import com.adammcneilly.pokedex.network.models.PokemonResponseDTO
import com.adammcneilly.pokedex.network.retrofit.RetrofitPokemonAPI

abstract class PokemonAPI(baseUrl: String) {
    abstract suspend fun getPokemon(): PokemonResponseDTO
    abstract suspend fun getPokemonDetail(pokemonName: String): PokemonDTO
}

class DefaultPokemonAPI(baseUrl: String) : PokemonAPI(baseUrl) {
    private val retrofitAPI = RetrofitPokemonAPI.defaultInstance(baseUrl)

    override suspend fun getPokemon(): PokemonResponseDTO {
        return retrofitAPI.getPokemonAsync().await()
    }

    override suspend fun getPokemonDetail(pokemonName: String): PokemonDTO {
        return retrofitAPI.getPokemonDetailAsync(pokemonName).await()
    }
}