package com.adammcneilly.pokedex.models

import com.squareup.moshi.Json

data class Sprites(
    @field:Json(name = "front_default")
    val frontDefault: String? = null
)