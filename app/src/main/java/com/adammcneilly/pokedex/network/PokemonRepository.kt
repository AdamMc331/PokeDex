package com.adammcneilly.pokedex.network

import com.adammcneilly.pokedex.models.Pokemon
import com.adammcneilly.pokedex.models.PokemonResponse

interface PokemonRepository {
    suspend fun getPokemon(): PokemonResponse
    suspend fun getPokemonDetail(pokemonName: String): Pokemon
}