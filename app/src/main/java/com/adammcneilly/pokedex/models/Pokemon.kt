package com.adammcneilly.pokedex.models

import com.adammcneilly.pokedex.database.models.PersistablePokemon
import com.adammcneilly.pokedex.database.models.PersistableTypeSlot
import com.adammcneilly.pokedex.network.models.PokemonDTO
import com.adammcneilly.pokedex.network.models.TypeSlotDTO

data class Pokemon(
    val name: String? = null,
    val frontSpriteUrl: String? = null,
    val url: String? = null,
    val types: List<TypeSlot>? = null
) {
    val sortedTypes: List<Type>
        get() = types?.sortedBy { it.slot }?.mapNotNull { it.type }.orEmpty()

    fun toPersistablePokemon(): PersistablePokemon {
        return PersistablePokemon(
            name = this.name.orEmpty(),
            frontSpriteUrl = this.frontSpriteUrl,
            url = this.url,
            types = this.types?.map(TypeSlot::toPersistableTypeSlot)
        )
    }
}

fun PersistablePokemon?.toPokemon(): Pokemon? {
    return this?.let { dto ->
        Pokemon(
            name = dto.name,
            frontSpriteUrl = dto.frontSpriteUrl,
            url = dto.url,
            types = dto.types?.mapNotNull(PersistableTypeSlot::toTypeSlot)
        )
    }
}

fun PokemonDTO?.toPokemon(): Pokemon? {
    return this?.let { dto ->
        Pokemon(
            name = dto.name,
            frontSpriteUrl = dto.sprites?.frontDefault,
            url = dto.url,
            types = dto.types?.mapNotNull(TypeSlotDTO::toTypeSlot)
        )
    }
}