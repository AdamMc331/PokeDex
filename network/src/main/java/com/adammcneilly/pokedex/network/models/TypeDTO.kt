package com.adammcneilly.pokedex.network.models

import android.annotation.SuppressLint
import com.adammcneilly.pokedex.core.Type
import com.squareup.moshi.Json

internal data class TypeDTO(
    @field:Json(name = "name")
    val name: String? = null,
    @field:Json(name = "url")
    val url: String? = null
) {

    @SuppressLint("DefaultLocale")
    fun toType(): Type? {
        return Type.values().find { type ->
            type.name == this.name?.toUpperCase()
        }
    }
}