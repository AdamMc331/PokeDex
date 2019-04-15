package com.adammcneilly.pokedex.detail

import com.adammcneilly.pokedex.models.Pokemon
import com.adammcneilly.pokedex.models.Species

data class DetailActivityState(
    val loading: Boolean = true,
    val pokemon: Pokemon? = null,
    val error: Throwable? = null,
    val species: Species? = null
)