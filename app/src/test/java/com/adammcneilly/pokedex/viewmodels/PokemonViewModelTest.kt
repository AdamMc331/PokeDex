package com.adammcneilly.pokedex.viewmodels

import com.adammcneilly.pokedex.models.Pokemon
import org.junit.Assert.assertEquals
import org.junit.Test

class PokemonViewModelTest {

    @Test
    fun getNameNoPokemon() {
        val viewModel = PokemonViewModel()
        assertEquals("", viewModel.name)
    }

    @Test
    fun getNameWithPokemon() {
        val testName = "Adam"
        val viewModel = PokemonViewModel()
        viewModel.pokemon = Pokemon(name = testName)
        assertEquals(testName, viewModel.name)
    }
}
