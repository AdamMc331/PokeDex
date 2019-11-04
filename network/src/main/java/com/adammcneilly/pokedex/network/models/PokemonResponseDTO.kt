package com.adammcneilly.pokedex.network.models

import com.squareup.moshi.Json

data class PokemonResponseDTO(
    @field:Json(name = "count")
    val count: Int? = null,
    @field:Json(name = "next")
    val next: String? = null,
    @field:Json(name = "previous")
    val previous: String? = null,
    @field:Json(name = "results")
    val results: List<PokemonDTO>? = null
)