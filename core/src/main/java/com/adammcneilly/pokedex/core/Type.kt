package com.adammcneilly.pokedex.core

enum class Type {
    NORMAL,
    FIGHTING,
    FLYING,
    POISON,
    GROUND,
    ROCK,
    BUG,
    GHOST,
    STEEL,
    FIRE,
    WATER,
    GRASS,
    ELECTRIC,
    PSYCHIC,
    ICE,
    DRAGON,
    DARK,
    FAIRY,
    UNKNOWN,
    SHADOW;

    companion object {
        fun fromString(input: String?): Type {
            return values().find { type ->
                type.name == input?.toUpperCase()
            } ?: UNKNOWN
        }
    }
}