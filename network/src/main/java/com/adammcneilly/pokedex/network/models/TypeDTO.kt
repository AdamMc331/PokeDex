package com.adammcneilly.pokedex.network.models

import com.adammcneilly.pokedex.core.Type
import com.squareup.moshi.Json

internal data class TypeDTO(
    @field:Json(name = "name")
    val name: String? = null,
    @field:Json(name = "url")
    val url: String? = null
) {

    fun toType(): Type {
        return Type.fromString(this.name)
    }
}