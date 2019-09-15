package com.adammcneilly.pokedex.models

import com.adammcneilly.pokedex.R
import org.junit.Assert.assertEquals
import org.junit.Test

class ColorTest {

    @Test
    fun toColorRes() {
        val blackColor = Color("black")
        assertEquals(R.color.mds_black, blackColor.toColorRes())

        val blueColor = Color("blue")
        assertEquals(R.color.mds_blue_500, blueColor.toColorRes())

        val brownColor = Color("brown")
        assertEquals(R.color.mds_brown_500, brownColor.toColorRes())

        val grayColor = Color("gray")
        assertEquals(R.color.mds_grey_500, grayColor.toColorRes())

        val greenColor = Color("green")
        assertEquals(R.color.mds_green_500, greenColor.toColorRes())

        val pinkColor = Color("pink")
        assertEquals(R.color.mds_pink_500, pinkColor.toColorRes())

        val redColor = Color("red")
        assertEquals(R.color.mds_red_500, redColor.toColorRes())

        val whiteColor = Color("white")
        assertEquals(R.color.mds_white, whiteColor.toColorRes())

        val yellowColor = Color("yellow")
        assertEquals(R.color.mds_yellow_500, yellowColor.toColorRes())

        val defaultColor = Color("blah")
        assertEquals(R.color.colorPrimary, defaultColor.toColorRes())
    }

    @Test
    fun getComplementaryColorRes() {
        val whiteColor = Color("white")
        assertEquals(R.color.mds_black, whiteColor.getComplementaryColorRes())

        val defaultColor = Color("blah")
        assertEquals(R.color.mds_white, defaultColor.getComplementaryColorRes())
    }
}
