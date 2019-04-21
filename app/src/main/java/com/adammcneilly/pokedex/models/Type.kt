package com.adammcneilly.pokedex.models

import com.adammcneilly.pokedex.R

data class Type(
    val name: String? = null,
    val url: String? = null
) {
    fun getColorRes(): Int {
        return when (name) {
            "water" -> R.color.type_water
            "normal" -> R.color.type_normal
            "fire" -> R.color.type_fire
            "electric" -> R.color.type_electric
            "grass" -> R.color.type_grass
            "ice" -> R.color.type_ice
            "fighting" -> R.color.type_fighting
            "poison" -> R.color.type_poison
            "ground" -> R.color.type_ground
            "flying" -> R.color.type_flying
            "psychic" -> R.color.type_psychic
            "bug" -> R.color.type_bug
            "rock" -> R.color.type_rock
            "ghost" -> R.color.type_ghost
            "dragon" -> R.color.type_dragon
            "dark" -> R.color.type_dark
            "steel" -> R.color.type_steel
            "fairy" -> R.color.type_fairy
            else -> R.color.type_normal
        }
    }

    fun getComplementaryColorRes(): Int {
        return when (name) {
            "steel" -> R.color.mds_black
            else -> R.color.mds_white
        }
    }
}