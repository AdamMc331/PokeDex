package com.adammcneilly.pokedex.detail

import com.adammcneilly.pokedex.core.Pokemon

sealed class PokemonDetailState {
    object Loading : PokemonDetailState()
    class Loaded(val pokemon: Pokemon) : PokemonDetailState()
    class Error(val error: Throwable?) : PokemonDetailState()
}