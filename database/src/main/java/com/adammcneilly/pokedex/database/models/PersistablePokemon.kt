package com.adammcneilly.pokedex.database.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class PersistablePokemon(
    @PrimaryKey val name: String = "",
    val frontSpriteUrl: String? = null,
    val types: List<PersistableTypeSlot>? = null
)