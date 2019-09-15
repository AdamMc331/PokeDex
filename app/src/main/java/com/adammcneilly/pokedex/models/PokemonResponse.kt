package com.adammcneilly.pokedex.models

data class PokemonResponse(
    val count: Int? = null,
    val next: String? = null,
    val previous: String? = null,
    val results: List<Pokemon>? = null
)
