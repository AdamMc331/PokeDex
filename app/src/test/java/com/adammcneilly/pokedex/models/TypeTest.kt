package com.adammcneilly.pokedex.models

import com.adammcneilly.pokedex.R
import com.adammcneilly.pokedex.database.models.PersistableType
import org.junit.Assert.assertEquals
import org.junit.Test

class TypeTest {

    @Test
    fun getColorRes() {
        val waterType = Type("water")
        assertEquals(R.color.type_water, waterType.getColorRes())

        val normalType = Type("normal")
        assertEquals(R.color.type_normal, normalType.getColorRes())

        val fireType = Type("fire")
        assertEquals(R.color.type_fire, fireType.getColorRes())

        val electricType = Type("electric")
        assertEquals(R.color.type_electric, electricType.getColorRes())

        val grassType = Type("grass")
        assertEquals(R.color.type_grass, grassType.getColorRes())

        val iceType = Type("ice")
        assertEquals(R.color.type_ice, iceType.getColorRes())

        val fightingType = Type("fighting")
        assertEquals(R.color.type_fighting, fightingType.getColorRes())

        val poisonType = Type("poison")
        assertEquals(R.color.type_poison, poisonType.getColorRes())

        val groundType = Type("ground")
        assertEquals(R.color.type_ground, groundType.getColorRes())

        val flyingType = Type("flying")
        assertEquals(R.color.type_flying, flyingType.getColorRes())

        val psychicType = Type("psychic")
        assertEquals(R.color.type_psychic, psychicType.getColorRes())

        val bugType = Type("bug")
        assertEquals(R.color.type_bug, bugType.getColorRes())

        val rockType = Type("rock")
        assertEquals(R.color.type_rock, rockType.getColorRes())

        val ghostType = Type("ghost")
        assertEquals(R.color.type_ghost, ghostType.getColorRes())

        val dragonType = Type("dragon")
        assertEquals(R.color.type_dragon, dragonType.getColorRes())

        val darkType = Type("dark")
        assertEquals(R.color.type_dark, darkType.getColorRes())

        val steelType = Type("steel")
        assertEquals(R.color.type_steel, steelType.getColorRes())

        val fairyType = Type("fairy")
        assertEquals(R.color.type_fairy, fairyType.getColorRes())

        val defaultType = Type("blahblahblah")
        assertEquals(R.color.type_normal, defaultType.getColorRes())
    }

    @Test
    fun getComplementaryColorRes() {
        val steelType = Type("steel")
        assertEquals(R.color.mds_black, steelType.getComplementaryColorRes())

        val defaultType = Type("blahblahblah")
        assertEquals(R.color.mds_white, defaultType.getComplementaryColorRes())
    }

    @Test
    fun mapToPersistableType() {
        val testName = "Test Name"
        val testURL = "Test URL"
        val type = Type(name = testName, url = testURL)

        val persistableType = type.toPersistableType()
        assertEquals(testName, persistableType.name)
        assertEquals(testURL, persistableType.url)
    }

    @Test
    fun mapFromPersistableType() {
        val testName = "Test Name"
        val testURL = "Test URL"
        val persistableType = PersistableType(name = testName, url = testURL)

        val type = persistableType.toType()
        assertEquals(testName, type?.name)
        assertEquals(testURL, type?.url)
    }
}