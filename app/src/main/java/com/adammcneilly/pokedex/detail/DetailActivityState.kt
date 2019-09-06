package com.adammcneilly.pokedex.detail

import com.adammcneilly.pokedex.models.Pokemon

sealed class DetailActivityState {
    object Loading : DetailActivityState()
    class Loaded(val pokemon: Pokemon) : DetailActivityState()
    class Error(val error: Throwable?) : DetailActivityState()
}