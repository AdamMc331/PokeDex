package com.adammcneilly.pokedex.network

import com.adammcneilly.pokedex.core.Pokemon
import com.adammcneilly.pokedex.core.PokemonResponse
import kotlinx.coroutines.flow.Flow

interface PokemonAPI {
    suspend fun getPokemon(): PokemonResponse
    suspend fun getPokemonDetail(pokemonName: String): Pokemon
    fun getPokemonDetailFlow(pokemonName: String): Flow<Pokemon>
}