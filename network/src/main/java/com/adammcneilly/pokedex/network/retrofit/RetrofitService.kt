package com.adammcneilly.pokedex.network.retrofit

import com.adammcneilly.pokedex.core.Pokemon
import com.adammcneilly.pokedex.core.PokemonResponse
import com.adammcneilly.pokedex.network.PokemonAPI
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class RetrofitService(baseUrl: String) : PokemonAPI {
    private val retrofitAPI = RetrofitPokemonAPI.defaultInstance(baseUrl)

    override suspend fun getPokemon(): PokemonResponse {
        return retrofitAPI.getPokemonAsync().await().toPokemonResponse()
    }

    override fun getPokemonDetailFlow(pokemonName: String): Flow<Pokemon> {
        return retrofitAPI.getPokemonDetailFlow(pokemonName).map {
            it.toPokemon()
        }
    }
}