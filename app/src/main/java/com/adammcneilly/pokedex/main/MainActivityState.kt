package com.adammcneilly.pokedex.main

import com.adammcneilly.pokedex.models.PokemonResponse

data class MainActivityState(
    val loading: Boolean = true,
    val data: PokemonResponse? = null,
    val error: Throwable? = null
)