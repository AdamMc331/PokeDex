package com.adammcneilly.pokedex.models

import com.adammcneilly.pokedex.database.models.PersistablePokemon
import com.adammcneilly.pokedex.database.models.PersistableTypeSlot
import com.adammcneilly.pokedex.network.models.PokemonDTO
import com.adammcneilly.pokedex.network.models.TypeSlotDTO

data class Pokemon(
    val name: String? = null,
    val sprites: Sprites? = null,
    val url: String? = null,
    val types: List<TypeSlot>? = null
) {
    val sortedTypes: List<Type>
        get() = types?.sortedBy { it.slot }?.mapNotNull { it.type }.orEmpty()

    fun toPersistablePokemon(): PersistablePokemon {
        return PersistablePokemon(
            name = this.name.orEmpty(),
            sprites = this.sprites?.toPersistableSprites(),
            url = this.url,
            types = this.types?.map(TypeSlot::toPersistableTypeSlot)
        )
    }

    fun toPokemonDTO(): PokemonDTO {
        return PokemonDTO(
            name = this.name,
            sprites = this.sprites?.toSpritesDTO(),
            url = this.url,
            types = this.types?.map(TypeSlot::toTypeSlotDTO)
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

fun PokemonDTO?.toPokemon(): Pokemon? {
    return this?.let { dto ->
        Pokemon(
            name = dto.name,
            sprites = dto.sprites?.toSprites(),
            url = dto.url,
            types = dto.types?.mapNotNull(TypeSlotDTO::toTypeSlot)
        )
    }
}