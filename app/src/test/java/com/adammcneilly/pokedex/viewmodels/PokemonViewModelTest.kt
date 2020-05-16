package com.adammcneilly.pokedex.viewmodels

import com.adammcneilly.pokedex.core.Pokemon
import org.junit.Assert.assertEquals
import org.junit.Test

class PokemonViewModelTest {

    @Test
    fun getNameNoPokemon() {
        val viewModel = PokemonViewModel()
        assertEquals("", viewModel.name)
    }

    @Test
    fun getImageUrlNoPokemon() {
        val viewModel = PokemonViewModel()
        assertEquals(null, viewModel.imageUrl)
    }

    @Test
    fun getNameWithPokemon() {
        val viewModel = PokemonViewModel()
        val pokemon = Pokemon(name = "Adam")
        viewModel.pokemon = pokemon
        assertEquals("Adam", viewModel.name)
    }

    @Test
    fun useExistingImageURLIfSupplied() {
        val viewModel = PokemonViewModel()
        val pokemon = Pokemon(frontSpriteUrl = "Adam")
        viewModel.pokemon = pokemon
        assertEquals("Adam", viewModel.imageUrl)
    }

    @Test
    fun formatNewUrlWithPokedexNumber() {
        val viewModel = PokemonViewModel()
        val pokemon = Pokemon(pokedexNumber = "1")
        viewModel.pokemon = pokemon
        assertEquals("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/1.png", viewModel.imageUrl)
    }
}