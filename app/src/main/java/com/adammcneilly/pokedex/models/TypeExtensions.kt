package com.adammcneilly.pokedex.models

import com.adammcneilly.pokedex.R
import com.adammcneilly.pokedex.core.Type

fun Type.colorRes(): Int {
    return when (this) {
        Type.WATER -> R.color.type_water
        Type.NORMAL -> R.color.type_normal
        Type.FIRE -> R.color.type_fire
        Type.FIGHTING -> R.color.type_fighting
        Type.ELECTRIC -> R.color.type_electric
        Type.FLYING -> R.color.type_flying
        Type.POISON -> R.color.type_poison
        Type.GROUND -> R.color.type_ground
        Type.ROCK -> R.color.type_rock
        Type.BUG -> R.color.type_bug
        Type.GHOST -> R.color.type_ghost
        Type.STEEL -> R.color.type_steel
        Type.GRASS -> R.color.type_grass
        Type.PSYCHIC -> R.color.type_psychic
        Type.ICE -> R.color.type_ice
        Type.DRAGON -> R.color.type_dragon
        Type.DARK -> R.color.type_dark
        Type.FAIRY -> R.color.type_fairy
        Type.UNKNOWN -> R.color.type_normal
        Type.SHADOW -> R.color.type_normal
    }
}

fun Type.complimentaryColorRes(): Int {
    return when (this) {
        Type.STEEL -> R.color.mds_black
        else -> R.color.mds_white
    }
}