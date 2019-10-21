package com.adammcneilly.pokedex.database.models

data class PersistableTypeSlot(
    val slot: Int? = null,
    val type: PersistableType? = null
)