package com.adammcneilly.pokedex.models

import com.adammcneilly.pokedex.database.models.PersistablePokemon
import com.adammcneilly.pokedex.database.models.PersistableTypeSlot

data class Pokemon(
    val name: String = "",
    val sprites: Sprites? = null,
    val url: String? = null,
    val types: List<TypeSlot>? = null
) {
    val sortedTypes: List<Type>
        get() = types?.sortedBy { it.slot }?.mapNotNull { it.type }.orEmpty()

    fun toPersistablePokemon(): PersistablePokemon {
        return PersistablePokemon(
            name = this.name,
            sprites = this.sprites?.toPersistableSprites(),
            url = this.url,
            types = this.types?.map(TypeSlot::toPersistableTypeSlot)
        )
    }
}

fun PersistablePokemon?.toPokemon(): Pokemon? {
    return this?.let { dto ->
        Pokemon(
            name = dto.name,
            sprites = dto.sprites?.toSprites(),
            url = dto.url,
            types = dto.types?.mapNotNull(PersistableTypeSlot::toTypeSlot)
        )
    }
}