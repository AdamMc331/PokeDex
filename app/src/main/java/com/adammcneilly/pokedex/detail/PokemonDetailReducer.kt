package com.adammcneilly.pokedex.detail

import android.util.Log
import com.adammcneilly.pokedex.redux.Action
import com.adammcneilly.pokedex.redux.Reducer

class PokemonDetailReducer : Reducer<PokemonDetailState> {
    override fun reduce(
        state: PokemonDetailState,
        action: Action
    ): PokemonDetailState {
        Log.d("LoggingMiddleware", "Reducing action: $action")

        return when (action) {
            is PokemonDetailAction.Loading -> PokemonDetailState.Loading
            is PokemonDetailAction.Loaded -> PokemonDetailState.Loaded(action.detail)
            is PokemonDetailAction.Error -> PokemonDetailState.Error(action.errorMessage)
            else -> state
        }
    }
}