package com.adammcneilly.pokedex.network.models

import com.squareup.moshi.Json

data class TypeSlotDTO(
    @field:Json(name = "slot")
    val slot: Int? = null,
    @field:Json(name = "type")
    val type: TypeDTO? = null
)