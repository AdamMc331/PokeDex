package com.adammcneilly.pokedex.network.models

import com.squareup.moshi.Json

data class PokemonDTO(
    @field:Json(name = "name")
    val name: String? = null,
    @field:Json(name = "sprites")
    val sprites: SpritesDTO? = null,
    @field:Json(name = "url")
    val url: String? = null,
    @field:Json(name = "types")
    val types: List<TypeSlotDTO>? = null
)