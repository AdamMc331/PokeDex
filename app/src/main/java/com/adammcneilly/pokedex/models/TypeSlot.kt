package com.adammcneilly.pokedex.models

import com.adammcneilly.pokedex.network.models.TypeSlotDTO

data class TypeSlot(
    val slot: Int? = null,
    val type: Type? = null
)

fun TypeSlotDTO?.toTypeSlot(): TypeSlot? {
    return this?.let {
        TypeSlot(
            slot = it.slot,
            type = it.type?.toType()
        )
    }
}