package com.adammcneilly.pokedex.pokemonlist

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.adammcneilly.pokedex.CoroutinesTestRule
import com.adammcneilly.pokedex.models.Pokemon
import com.adammcneilly.pokedex.models.PokemonResponse
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
    fun loadData() {
        val testResult = PokemonResponse(count = 10)

        testRobot.mockPokemonResponse(testResult)
            .buildViewModel()
            .assertShowLoading(false)
            .assertShowError(false)
            .assertShowData(true)
    }

    @Test
    fun loadError() {
        testRobot.mockPokemonResponseError()
            .buildViewModel()
            .assertShowLoading(false)
            .assertShowData(false)
            .assertShowError(true)
    }

    @Test
    fun getPokemon() {
        val testPokemon = listOf(Pokemon(name = "Adam"))
        val testResult = PokemonResponse(results = testPokemon)

        testRobot.mockPokemonResponse(testResult)
            .buildViewModel()
            .assertShowLoading(false)
            .assertShowError(false)
            .assertShowData(true)
            .assertPokemonList(testPokemon)
    }
}
