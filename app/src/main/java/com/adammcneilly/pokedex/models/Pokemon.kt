package com.adammcneilly.pokedex.models

import com.adammcneilly.pokedex.database.models.PersistablePokemon
import com.adammcneilly.pokedex.network.models.PokemonDTO
import com.adammcneilly.pokedex.network.models.TypeSlotDTO

data class Pokemon(
    val name: String? = null,
    val frontSpriteUrl: String? = null,
    val types: List<TypeSlot>? = null
) {
    val sortedTypes: List<Type>
        get() = types?.sortedBy { it.slot }?.mapNotNull { it.type }.orEmpty()

    fun toPersistablePokemon(): PersistablePokemon {
        val orderedTypes = this.types
            ?.sortedBy { typeSlot ->
                typeSlot.slot
            }
            ?.map { typeSlot ->
                typeSlot.type?.toPersistableType()
            }

        return PersistablePokemon(
            name = this.name.orEmpty(),
            frontSpriteUrl = this.frontSpriteUrl,
            firstType = orderedTypes?.getOrNull(0),
            secondType = orderedTypes?.getOrNull(1)
        )
    }
}

fun PersistablePokemon?.toPokemon(): Pokemon? {
    return this?.let { dto ->
        val firstTypeSlot = TypeSlot(
            slot = 1,
            type = dto.firstType?.toType()
        )

        val secondTypeSlot = TypeSlot(
            slot = 2,
            type = dto.secondType?.toType()
        )

        Pokemon(
            name = dto.name,
            frontSpriteUrl = dto.frontSpriteUrl,
            types = listOf(firstTypeSlot, secondTypeSlot)
        )
    }
}

fun PokemonDTO?.toPokemon(): Pokemon? {
    return this?.let { dto ->
        Pokemon(
            name = dto.name,
            frontSpriteUrl = dto.sprites?.frontDefault,
            types = dto.types?.mapNotNull(TypeSlotDTO::toTypeSlot)
        )
    }
}