package com.adammcneilly.pokedex.network.models

import com.adammcneilly.pokedex.core.Pokemon
import com.adammcneilly.pokedex.core.Type
import com.squareup.moshi.Json

internal data class PokemonDTO(
    @field:Json(name = "name")
    val name: String? = null,
    @field:Json(name = "sprites")
    val sprites: SpritesDTO? = null,
    @field:Json(name = "url")
    val url: String? = null,
    @field:Json(name = "types")
    val types: List<TypeSlotDTO>? = null
) {

    fun toPokemon(): Pokemon {
        val orderedTypes = this.types
            ?.sortedBy { typeSlot ->
                typeSlot.slot
            }
            ?.map { typeSlot ->
                typeSlot.type?.toType()
            }

        return Pokemon(
            name = this.name.orEmpty(),
            frontSpriteUrl = this.sprites?.frontDefault,
            firstType = orderedTypes?.getOrNull(0) ?: Type.UNKNOWN,
            secondType = orderedTypes?.getOrNull(1)
        )
    }
}