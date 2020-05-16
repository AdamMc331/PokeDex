package com.adammcneilly.pokedex.viewmodels

import com.adammcneilly.pokedex.core.Pokemon
import org.junit.Before
import org.junit.Test

class PokemonViewModelTest {
    private lateinit var testRobot: PokemonViewModelRobot

    @Before
    fun setUp() {
        testRobot = PokemonViewModelRobot()
    }

    @Test
    fun getNameNoPokemon() {
        testRobot
            .setPokemon(null)
            .assertPokemonName("")
    }

    @Test
    fun getImageUrlNoPokemon() {
        testRobot
            .setPokemon(null)
            .assertImageUrl(null)
    }

    @Test
    fun getNameWithPokemon() {
        val pokemonName = "Adam"
        val pokemon = Pokemon(name = pokemonName)

        testRobot
            .setPokemon(pokemon)
            .assertPokemonName(pokemonName)
    }

    @Test
    fun useExistingImageURLIfSupplied() {
        val frontImage = "Adam"
        val pokemon = Pokemon(frontSpriteUrl = frontImage)

        testRobot
            .setPokemon(pokemon)
            .assertImageUrl(frontImage)
    }

    @Test
    fun formatNewUrlWithPokedexNumber() {
        val pokemon = Pokemon(pokedexNumber = "1")

        testRobot
            .setPokemon(pokemon)
            .assertImageUrl("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/1.png")
    }
}