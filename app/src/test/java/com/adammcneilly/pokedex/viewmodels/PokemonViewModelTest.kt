package com.adammcneilly.pokedex.viewmodels

import com.adammcneilly.pokedex.models.Pokemon
import com.adammcneilly.pokedex.models.Type
import com.adammcneilly.pokedex.models.TypeSlot
import org.junit.Assert.assertEquals
import org.junit.Test

class PokemonViewModelTest {

    @Test
    fun getNameNoPokemon() {
        val viewModel = PokemonViewModel()
        assertEquals("", viewModel.name)
    }

    @Test
    fun getTypesNoValue() {
        val viewModel = PokemonViewModel()
        assertEquals(emptyList<String>(), viewModel.types)

        viewModel.pokemon = Pokemon()
        assertEquals(emptyList<String>(), viewModel.types)
    }

    @Test
    fun getNameWithPokemon() {
        val testName = "Adam"
        val viewModel = PokemonViewModel()
        viewModel.pokemon = Pokemon(name = testName)
        assertEquals(testName, viewModel.name)
    }

    @Test
    fun getTypesSingleType() {
        val type = Type(name = "poison")
        val pokemon = Pokemon(
            types = listOf(
                TypeSlot(1, type = type)
            )
        )

        val viewModel = PokemonViewModel()
        viewModel.pokemon = pokemon

        val expected = listOf(type)
        assertEquals(expected, viewModel.types)
    }

    @Test
    fun getTypesMultipleTypes() {
        val secondType = Type(name = "poison")
        val firstType = Type(name = "ghost")
        val pokemon = Pokemon(
            types = listOf(
                TypeSlot(2, type = secondType),
                TypeSlot(1, type = firstType)
            )
        )

        val viewModel = PokemonViewModel()
        viewModel.pokemon = pokemon

        val expected = listOf(firstType, secondType)
        assertEquals(expected, viewModel.types)
    }
}
