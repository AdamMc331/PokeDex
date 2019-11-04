package com.adammcneilly.pokedex.network.models

import com.squareup.moshi.Json

data class TypeDTO(
    @field:Json(name = "name")
    val name: String? = null,
    @field:Json(name = "url")
    val url: String? = null
)