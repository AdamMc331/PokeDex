package com.adammcneilly.pokedex.detail

import com.adammcneilly.pokedex.models.Pokemon

data class DetailActivityState(
    val loading: Boolean = true,
    val pokemon: Pokemon? = null,
    val error: Throwable? = null
)