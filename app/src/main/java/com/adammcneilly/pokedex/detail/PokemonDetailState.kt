package com.adammcneilly.pokedex.detail

import com.adammcneilly.pokedex.models.Pokemon

sealed class PokemonDetailState {
    object Loading : PokemonDetailState()
    class Loaded(val pokemon: Pokemon) : PokemonDetailState()
    class Error(val error: Throwable?) : PokemonDetailState()
}