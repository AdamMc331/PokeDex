package com.adammcneilly.pokedex.data

import com.adammcneilly.pokedex.core.PokemonResponse
import com.adammcneilly.pokedex.detail.PokemonDetailAction
import kotlinx.coroutines.flow.Flow

interface PokemonRepository {
    fun getPokemon(): Flow<Result<PokemonResponse>>
    fun getPokemonDetail(pokemonName: String): Flow<PokemonDetailAction>
}