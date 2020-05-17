package com.adammcneilly.pokedex.pokemonlist

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.adammcneilly.pokedex.CoroutinesTestRule
import com.adammcneilly.pokedex.core.Pokemon
import com.adammcneilly.pokedex.core.PokemonResponse
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
@Suppress("UNCHECKED_CAST")
class PokemonListViewModelTest {
    @JvmField
    @Rule
    val instantTaskExecutor = InstantTaskExecutorRule()

    @JvmField
    @Rule
    val coroutinesTestRule = CoroutinesTestRule()

    private val testRobot = PokemonListViewModelRobot()

    @Test
    fun showLoadingBeforeData() {
        val testResult = PokemonResponse()

        testRobot
            .buildViewModel()
            .assertShowLoading(true)
            .assertShowData(false)
            .assertShowError(false)

        testRobot
            .mockPokemonResponse(testResult)
            .assertShowLoading(false)
            .assertShowError(false)
            .assertShowData(true)
    }

    @Test
    fun loadError() {
        testRobot
            .buildViewModel()
            .mockPokemonResponseError()
            .assertShowLoading(false)
            .assertShowData(false)
            .assertShowError(true)
    }

    @Test
    fun getPokemon() {
        val testPokemon = listOf(Pokemon(name = "Adam"))
        val testResult = PokemonResponse(results = testPokemon)

        testRobot
            .buildViewModel()
            .mockPokemonResponse(testResult)
            .assertShowLoading(false)
            .assertShowError(false)
            .assertShowData(true)
            .assertPokemonList(testPokemon)
    }
}