package com.adammcneilly.pokedex.detail

import com.adammcneilly.pokedex.core.Pokemon
import com.adammcneilly.pokedex.redux.State

sealed class PokemonDetailState : State {
    object Loading : PokemonDetailState()
    class Loaded(val pokemon: Pokemon) : PokemonDetailState()
    class Error(val errorMessage: String) : PokemonDetailState()
}