package com.adammcneilly.pokedex.core

data class Pokemon(
    val name: String = "",
    val frontSpriteUrl: String? = null,
    val firstType: Type = Type.UNKNOWN,
    val secondType: Type? = null,
    val pokedexNumber: String = ""
)