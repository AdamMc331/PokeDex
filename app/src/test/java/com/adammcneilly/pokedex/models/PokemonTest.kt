package com.adammcneilly.pokedex.models

import com.adammcneilly.pokedex.database.models.PersistablePokemon
import com.adammcneilly.pokedex.database.models.PersistableType
import com.adammcneilly.pokedex.database.models.PersistableTypeSlot
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
        val testUrl = "Test URL"
        val testTypes = listOf(TypeSlot(1, Type("Test")))
        val pokemon = Pokemon(
            name = testName,
            frontSpriteUrl = testSprites,
            url = testUrl,
            types = testTypes
        )

        val persistablePokemon = pokemon.toPersistablePokemon()
        assertEquals(testName, persistablePokemon.name)
        assertEquals(testSprites, persistablePokemon.frontSpriteUrl)
        assertEquals(testUrl, persistablePokemon.url)
        assertEquals(testTypes.map(TypeSlot::toPersistableTypeSlot), persistablePokemon.types)
    }

    @Test
    fun mapFromPersistablePokemon() {
        val testName = "Test Name"
        val testSprites = "Test"
        val testUrl = "Test URL"
        val testTypes = listOf(PersistableTypeSlot(1, PersistableType("Test")))
        val persistablePokemon = PersistablePokemon(
            name = testName,
            frontSpriteUrl = testSprites,
            url = testUrl,
            types = testTypes
        )

        val pokemon = persistablePokemon.toPokemon()
        assertEquals(testName, pokemon?.name)
        assertEquals(testSprites, pokemon?.frontSpriteUrl)
        assertEquals(testUrl, pokemon?.url)
        assertEquals(testTypes.map(PersistableTypeSlot::toTypeSlot), pokemon?.types)
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
        val testUrl = "Test URL"
        val testTypes = listOf(TypeSlotDTO(1, TypeDTO("Test")))
        val pokemonDTO = PokemonDTO(
            name = testName,
            sprites = testSprites,
            url = testUrl,
            types = testTypes
        )

        val pokemon = pokemonDTO.toPokemon()
        assertEquals(testName, pokemon?.name)
        assertEquals(testSprites.frontDefault, pokemon?.frontSpriteUrl)
        assertEquals(testUrl, pokemon?.url)
        assertEquals(testTypes.map(TypeSlotDTO::toTypeSlot), pokemon?.types)
    }
}