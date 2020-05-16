package com.adammcneilly.pokedex.viewmodels

import com.adammcneilly.pokedex.core.Pokemon
import org.junit.Assert.assertEquals

class PokemonViewModelRobot {
    private val viewModel = PokemonViewModel()

    fun setPokemon(pokemon: Pokemon?) = apply {
        viewModel.pokemon = pokemon
    }

    fun assertPokemonName(expectedName: String) = apply {
        assertEquals(expectedName, viewModel.name)
    }

    fun assertImageUrl(expectedImageUrl: String?) = apply {
        assertEquals(expectedImageUrl, viewModel.imageUrl)
    }
}