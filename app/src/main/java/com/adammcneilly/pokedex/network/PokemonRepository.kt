package com.adammcneilly.pokedex.network

import com.adammcneilly.pokedex.models.Pokemon
import com.adammcneilly.pokedex.models.PokemonResponse

open class PokemonRepository(private val api: PokemonAPI) {
    suspend fun getPokemon(): PokemonResponse {
        return api.getPokemonAsync().await()
    }

    suspend fun getPokemonDetail(name: String): Pokemon {
        return api.getPokemonDetailAsync(name).await()
    }
}