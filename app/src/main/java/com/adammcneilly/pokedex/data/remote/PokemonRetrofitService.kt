package com.adammcneilly.pokedex.data.remote

import com.adammcneilly.pokedex.data.PokemonRepository
import com.adammcneilly.pokedex.models.Pokemon
import com.adammcneilly.pokedex.models.PokemonResponse

open class PokemonRetrofitService(private val api: PokemonAPI) :
    PokemonRepository {
    override suspend fun getPokemon(): PokemonResponse {
        return api.getPokemonAsync().await()
    }

    override suspend fun getPokemonDetail(pokemonName: String): Pokemon {
        return api.getPokemonDetailAsync(pokemonName).await()
    }
}
