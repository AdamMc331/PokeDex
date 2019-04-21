package com.adammcneilly.pokedex.viewmodels

import org.junit.Assert.assertEquals
import org.junit.Test

class PokemonViewModelTest {

    @Test
    fun getNameNoPokemon() {
        val viewModel = PokemonViewModel()
        assertEquals("", viewModel.name)
    }
}
