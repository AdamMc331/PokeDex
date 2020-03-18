package com.adammcneilly.pokedex.database.models

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class PersistablePokemon(
    @PrimaryKey val name: String = "",
    val frontSpriteUrl: String? = null,
    @Embedded(prefix = "firstType_")
    val firstType: PersistableType? = null,
    @Embedded(prefix = "secondType_")
    val secondType: PersistableType? = null
)