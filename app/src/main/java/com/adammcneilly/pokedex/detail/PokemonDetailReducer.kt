package com.adammcneilly.pokedex.detail

import com.adammcneilly.pokedex.redux.Reducer

class PokemonDetailReducer : Reducer<PokemonDetailState, PokemonDetailAction> {
    override fun reduce(
        state: PokemonDetailState,
        action: PokemonDetailAction
    ): PokemonDetailState {
        return when (action) {
            is PokemonDetailAction.Loading -> PokemonDetailState.Loading
            is PokemonDetailAction.Loaded -> PokemonDetailState.Loaded(action.detail)
            is PokemonDetailAction.Error -> PokemonDetailState.Error(action.errorMessage)
            else -> state
        }
    }
}