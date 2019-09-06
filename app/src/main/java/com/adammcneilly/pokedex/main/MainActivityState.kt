package com.adammcneilly.pokedex.main

import com.adammcneilly.pokedex.models.PokemonResponse

sealed class MainActivityState {
    object Loading : MainActivityState()
    class Loaded(val data: PokemonResponse) : MainActivityState()
    class Error(val error: Throwable?) : MainActivityState()
}