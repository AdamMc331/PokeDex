package com.adammcneilly.pokedex.models

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Pokemon(
    @PrimaryKey val name: String = "",
    @Embedded val sprites: Sprites? = null,
    val url: String? = null,
    val types: List<TypeSlot>? = null
) {
    val sortedTypes: List<Type>
        get() = types?.sortedBy { it.slot }?.mapNotNull { it.type }.orEmpty()
}
