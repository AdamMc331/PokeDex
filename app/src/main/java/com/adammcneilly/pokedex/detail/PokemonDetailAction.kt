package com.adammcneilly.pokedex.detail

import com.adammcneilly.pokedex.core.Pokemon
import com.adammcneilly.pokedex.redux.Action

sealed class PokemonDetailAction : Action {
    object Loading : PokemonDetailAction()
    class Loaded(val detail: Pokemon) : PokemonDetailAction()
    class Error(val errorMessage: String) : PokemonDetailAction()
}