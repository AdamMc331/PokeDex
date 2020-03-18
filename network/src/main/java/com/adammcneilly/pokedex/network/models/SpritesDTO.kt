package com.adammcneilly.pokedex.network.models

import com.squareup.moshi.Json

internal data class SpritesDTO(
    @field:Json(name = "front_default")
    val frontDefault: String? = null
)