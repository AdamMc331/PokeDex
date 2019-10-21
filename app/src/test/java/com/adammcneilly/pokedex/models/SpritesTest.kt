package com.adammcneilly.pokedex.models

import com.adammcneilly.pokedex.database.models.PersistableSprites
import org.junit.Assert.assertEquals
import org.junit.Test

class SpritesTest {
    @Test
    fun mapToPersistableSprites() {
        val testFrontDefault = "Front Default"
        val sprites = Sprites(frontDefault = testFrontDefault)

        val persistableSprites = sprites.toPersistableSprites()
        assertEquals(testFrontDefault, persistableSprites.frontDefault)
    }

    @Test
    fun mapFromPersistableSprites() {
        val testFrontDefault = "Front Default"
        val persistableSprites = PersistableSprites(frontDefault = testFrontDefault)

        val sprites = persistableSprites.toSprites()
        assertEquals(testFrontDefault, sprites?.frontDefault)
    }
}