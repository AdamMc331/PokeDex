package com.adammcneilly.pokedex.models

import com.adammcneilly.pokedex.database.models.PersistableTypeSlot
import com.adammcneilly.pokedex.network.models.TypeSlotDTO

data class TypeSlot(
    val slot: Int? = null,
    val type: Type? = null
) {
    fun toPersistableTypeSlot(): PersistableTypeSlot {
        return PersistableTypeSlot(
            slot = this.slot,
            type = this.type?.toPersistableType()
        )
    }

    fun toTypeSlotDTO(): TypeSlotDTO {
        return TypeSlotDTO(
            slot = this.slot,
            type = this.type?.toTypeDTO()
        )
    }
}

fun PersistableTypeSlot?.toTypeSlot(): TypeSlot? {
    return this?.let {
        TypeSlot(
            slot = it.slot,
            type = it.type?.toType()
        )
    }
}

fun TypeSlotDTO?.toTypeSlot(): TypeSlot? {
    return this?.let {
        TypeSlot(
            slot = it.slot,
            type = it.type?.toType()
        )
    }
}