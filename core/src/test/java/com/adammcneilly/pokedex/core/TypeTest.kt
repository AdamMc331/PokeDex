package com.adammcneilly.pokedex.core

import org.junit.Assert.assertEquals
import org.junit.Test

class TypeTest {
    @Test
    fun fromString() {
        assertEquals(Type.GRASS, Type.fromString("grass"))
        assertEquals(Type.UNKNOWN, Type.fromString("blahblahblah"))
    }
}