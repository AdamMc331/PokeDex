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
        val testTypes = listOf(TypeSlot(1, Type("Test")))
        val pokemon = Pokemon(
            name = testName,
            frontSpriteUrl = testSprites,
            types = testTypes
        )

        val persistablePokemon = pokemon.toPersistablePokemon()
        assertEquals(testName, persistablePokemon.name)
        assertEquals(testSprites, persistablePokemon.frontSpriteUrl)
        assertEquals(testTypes.getOrNull(0)?.type?.toPersistableType(), persistablePokemon.firstType)
        assertEquals(testTypes.getOrNull(1)?.type?.toPersistableType(), persistablePokemon.secondType)
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
        assertEquals(testTypes.getOrNull(0)?.toType(), pokemon?.types?.getOrNull(0)?.type)
        assertEquals(testTypes.getOrNull(1)?.toType(), pokemon?.types?.getOrNull(1)?.type)
    }

    @Test
    fun sortTypes() {
        val secondTypeSlot = TypeSlot(2, Type("Second"))
        val firstTypeSlot = TypeSlot(1, Type("First"))
        val disorderedTypes = listOf(secondTypeSlot, firstTypeSlot)

        val pokemon = Pokemon(types = disorderedTypes)
        val sortedTypes = pokemon.sortedTypes
        val expectedTypes = listOf(firstTypeSlot.type, secondTypeSlot.type)
        assertEquals(expectedTypes, sortedTypes)
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
        assertEquals(testTypes.map(TypeSlotDTO::toTypeSlot), pokemon?.types)
    }
}