package com.adammcneilly.pokedex.models

import com.adammcneilly.pokedex.R

data class Color(
    val name: String? = null,
    val url: String? = null
) {
    fun toColorRes(): Int {
        return when (name) {
            "black" -> R.color.mds_black
            "blue" -> R.color.mds_blue_500
            "brown" -> R.color.mds_brown_500
            "gray" -> R.color.mds_grey_500
            "green" -> R.color.mds_green_500
            "pink" -> R.color.mds_pink_500
            "purple" -> R.color.mds_purple_500
            "red" -> R.color.mds_red_500
            "white" -> R.color.mds_white
            "yellow" -> R.color.mds_yellow_500
            else -> R.color.colorPrimary
        }
    }

    fun getComplementaryColorRes(): Int {
        return when (name) {
            "white" -> R.color.mds_black
            else -> R.color.mds_white
        }
    }
}