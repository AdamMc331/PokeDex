package com.adammcneilly.pokedex.models

import com.adammcneilly.pokedex.database.models.PersistablePokemon
import com.adammcneilly.pokedex.network.models.PokemonDTO

data class Pokemon(
    val name: String? = null,
    val frontSpriteUrl: String? = null,
    val firstType: Type? = null,
    val secondType: Type? = null
) {
    fun toPersistablePokemon(): PersistablePokemon {
        return PersistablePokemon(
            name = this.name.orEmpty(),
            frontSpriteUrl = this.frontSpriteUrl,
            firstType = this.firstType?.toPersistableType(),
            secondType = this.secondType?.toPersistableType()
        )
    }
}

fun PersistablePokemon?.toPokemon(): Pokemon? {
    return this?.let { dto ->
        Pokemon(
            name = dto.name,
            frontSpriteUrl = dto.frontSpriteUrl,
            firstType = dto.firstType?.toType(),
            secondType = dto.secondType?.toType()
        )
    }
}

fun PokemonDTO?.toPokemon(): Pokemon? {
    return this?.let { dto ->
        val orderedSlots = dto.types?.sortedBy { typeSlotDTO ->
            typeSlotDTO.slot
        }

        Pokemon(
            name = dto.name,
            frontSpriteUrl = dto.sprites?.frontDefault,
            firstType = orderedSlots?.getOrNull(0)?.type?.toType(),
            secondType = orderedSlots?.getOrNull(1)?.type?.toType()
        )
    }
}