package com.adammcneilly.pokedex.data

import com.adammcneilly.pokedex.core.Pokemon
import com.adammcneilly.pokedex.core.PokemonResponse

interface PokemonRepository {
    suspend fun getPokemon(): PokemonResponse?
    suspend fun getPokemonDetail(pokemonName: String): Pokemon?
}