package com.adammcneilly.pokedex.models

import com.adammcneilly.pokedex.database.models.PersistableSprites
import com.adammcneilly.pokedex.network.models.SpritesDTO
import com.squareup.moshi.Json

data class Sprites(
    @field:Json(name = "front_default")
    val frontDefault: String? = null
) {
    fun toPersistableSprites(): PersistableSprites {
        return PersistableSprites(
            frontDefault = this.frontDefault
        )
    }

    fun toSpritesDTO(): SpritesDTO {
        return SpritesDTO(
            frontDefault = this.frontDefault
        )
    }
}

fun PersistableSprites?.toSprites(): Sprites? {
    return this?.let {
        Sprites(
            frontDefault = it.frontDefault
        )
    }
}

fun SpritesDTO?.toSprites(): Sprites? {
    return this?.let {
        Sprites(
            frontDefault = it.frontDefault
        )
    }
}