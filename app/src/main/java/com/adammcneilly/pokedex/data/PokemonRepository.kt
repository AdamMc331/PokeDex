package com.adammcneilly.pokedex.data

import com.adammcneilly.pokedex.core.Pokemon
import com.adammcneilly.pokedex.core.PokemonResponse
import kotlinx.coroutines.flow.Flow

interface PokemonRepository {
    suspend fun getPokemon(): PokemonResponse?
    fun getPokemonDetail(pokemonName: String): Flow<Result<Pokemon>>
}