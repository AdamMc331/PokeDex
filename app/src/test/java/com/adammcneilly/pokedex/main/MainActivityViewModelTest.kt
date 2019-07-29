package com.adammcneilly.pokedex.main

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.adammcneilly.pokedex.models.Pokemon
import com.adammcneilly.pokedex.models.PokemonResponse
import org.junit.Rule
import org.junit.Test

@Suppress("UNCHECKED_CAST")
class MainActivityViewModelTest {
    @JvmField
    @Rule
    val instantTaskExecutor = InstantTaskExecutorRule()

    private val testRobot = MainActivityViewModelRobot()

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