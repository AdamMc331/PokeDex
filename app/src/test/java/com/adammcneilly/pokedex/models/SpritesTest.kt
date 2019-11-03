package com.adammcneilly.pokedex.models

import com.adammcneilly.pokedex.database.models.PersistableSprites
import com.adammcneilly.pokedex.network.models.SpritesDTO
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

    @Test
    fun mapToSpritesDTO() {
        val testFrontDefault = "Front Default"
        val sprites = Sprites(frontDefault = testFrontDefault)

        val spritesDTO = sprites.toSpritesDTO()
        assertEquals(testFrontDefault, spritesDTO.frontDefault)
    }

    @Test
    fun mapFromSpritesDTO() {
        val testFrontDefault = "Front Default"
        val spritesDTO = SpritesDTO(frontDefault = testFrontDefault)

        val sprites = spritesDTO.toSprites()
        assertEquals(testFrontDefault, sprites?.frontDefault)
    }
}