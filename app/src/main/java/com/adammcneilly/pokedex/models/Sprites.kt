package com.adammcneilly.pokedex.models

import com.adammcneilly.pokedex.network.models.SpritesDTO

data class Sprites(
    val frontDefault: String? = null
) {
    fun toSpritesDTO(): SpritesDTO {
        return SpritesDTO(
            frontDefault = this.frontDefault
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