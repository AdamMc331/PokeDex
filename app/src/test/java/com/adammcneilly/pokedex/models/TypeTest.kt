package com.adammcneilly.pokedex.models

import com.adammcneilly.pokedex.R
import com.adammcneilly.pokedex.core.Type
import org.junit.Assert.assertEquals
import org.junit.Test

class TypeTest {

    @Test
    fun colorRes() {
        val waterType = Type.WATER
        assertEquals(R.color.type_water, waterType.colorRes())

        val normalType = Type.NORMAL
        assertEquals(R.color.type_normal, normalType.colorRes())

        val fireType = Type.FIRE
        assertEquals(R.color.type_fire, fireType.colorRes())

        val electricType = Type.ELECTRIC
        assertEquals(R.color.type_electric, electricType.colorRes())

        val grassType = Type.GRASS
        assertEquals(R.color.type_grass, grassType.colorRes())

        val iceType = Type.ICE
        assertEquals(R.color.type_ice, iceType.colorRes())

        val fightingType = Type.FIGHTING
        assertEquals(R.color.type_fighting, fightingType.colorRes())

        val poisonType = Type.POISON
        assertEquals(R.color.type_poison, poisonType.colorRes())

        val groundType = Type.GROUND
        assertEquals(R.color.type_ground, groundType.colorRes())

        val flyingType = Type.FLYING
        assertEquals(R.color.type_flying, flyingType.colorRes())

        val psychicType = Type.PSYCHIC
        assertEquals(R.color.type_psychic, psychicType.colorRes())

        val bugType = Type.BUG
        assertEquals(R.color.type_bug, bugType.colorRes())

        val rockType = Type.ROCK
        assertEquals(R.color.type_rock, rockType.colorRes())

        val ghostType = Type.GHOST
        assertEquals(R.color.type_ghost, ghostType.colorRes())

        val dragonType = Type.DRAGON
        assertEquals(R.color.type_dragon, dragonType.colorRes())

        val darkType = Type.DARK
        assertEquals(R.color.type_dark, darkType.colorRes())

        val steelType = Type.STEEL
        assertEquals(R.color.type_steel, steelType.colorRes())

        val fairyType = Type.FAIRY
        assertEquals(R.color.type_fairy, fairyType.colorRes())

        val defaultType = Type.UNKNOWN
        assertEquals(R.color.type_normal, defaultType.colorRes())
    }

    @Test
    fun getComplementaryColorRes() {
        val steelType = Type.STEEL
        assertEquals(R.color.mds_black, steelType.complimentaryColorRes())

        val defaultType = Type.UNKNOWN
        assertEquals(R.color.mds_white, defaultType.complimentaryColorRes())
    }
}