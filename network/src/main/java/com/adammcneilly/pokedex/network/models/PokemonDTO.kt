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
    val types: List<TypeSlotDTO>? = null,
    @field:Json(name = "id")
    val id: String? = null
) {

    /**
     * The pokeapi.co API does not give pokedexNumber as a property when we fetch from the
     * list endpoint, so we have to parse it from the [url] property.
     *
     * If we were fetching from the detail endpoint of the API, we can use [id].
     *
     * To parse the string from [url], we can remove the base endpoint string and the trailing slash
     * to get the ID. Sample url: https://pokeapi.co/api/v2/pokemon/1/
     *
     * TODO: In a future PR, let's use better DI here so we don't have hardcoded API urls. Not sure
     *  how to refactor that right this moment.
     */
    private fun parsePokedexNumber(): String {
        if (id != null) return id

        return this.url
            ?.removePrefix("https://pokeapi.co/api/v2/pokemon/")
            ?.removeSuffix("/")
            .orEmpty()
    }

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
            secondType = orderedTypes?.getOrNull(1),
            pokedexNumber = parsePokedexNumber()
        )
    }
}