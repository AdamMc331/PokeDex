package com.adammcneilly.pokedex.models

import com.adammcneilly.pokedex.database.models.PersistablePokemon
import com.adammcneilly.pokedex.database.models.PersistableType
import com.adammcneilly.pokedex.network.models.PokemonDTO
import com.adammcneilly.pokedex.network.models.SpritesDTO
import com.adammcneilly.pokedex.network.models.TypeDTO
import com.adammcneilly.pokedex.network.models.TypeSlotDTO
import org.junit.Assert.assertEquals
import org.junit.Test

class PokemonTest {
    @Test
    fun mapToPersistablePokemon() {
        val testName = "Test Name"
        val testSprites = "Test"
        val firstType = Type("Test")
        val pokemon = Pokemon(
            name = testName,
            frontSpriteUrl = testSprites,
            firstType = firstType
        )

        val persistablePokemon = pokemon.toPersistablePokemon()
        assertEquals(testName, persistablePokemon.name)
        assertEquals(testSprites, persistablePokemon.frontSpriteUrl)
        assertEquals(firstType.toPersistableType(), persistablePokemon.firstType)
        assertEquals(null, persistablePokemon.secondType)
    }

    @Test
    fun mapFromPersistablePokemon() {
        val testName = "Test Name"
        val testSprites = "Test"
        val testTypes = listOf(PersistableType("Test"))
        val persistablePokemon = PersistablePokemon(
            name = testName,
            frontSpriteUrl = testSprites,
            firstType = testTypes.getOrNull(0)
        )

        val pokemon = persistablePokemon.toPokemon()
        assertEquals(testName, pokemon?.name)
        assertEquals(testSprites, pokemon?.frontSpriteUrl)
        assertEquals(testTypes.getOrNull(0)?.toType(), pokemon?.firstType)
        assertEquals(testTypes.getOrNull(1)?.toType(), pokemon?.secondType)
    }

    @Test
    fun mapFromPokemonDTO() {
        val testName = "Test Name"
        val testSprites = SpritesDTO("Test")
        val testTypes = listOf(TypeSlotDTO(1, TypeDTO("Test")))
        val pokemonDTO = PokemonDTO(
            name = testName,
            sprites = testSprites,
            types = testTypes
        )

        val pokemon = pokemonDTO.toPokemon()
        assertEquals(testName, pokemon?.name)
        assertEquals(testSprites.frontDefault, pokemon?.frontSpriteUrl)
        assertEquals(testTypes.getOrNull(0)?.type?.toType(), pokemon?.firstType)
        assertEquals(testTypes.getOrNull(1)?.type?.toType(), pokemon?.secondType)
    }
}