package com.adammcneilly.pokedex.main

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.adammcneilly.pokedex.DispatcherProvider
import com.adammcneilly.pokedex.models.Pokemon
import com.adammcneilly.pokedex.models.PokemonResponse
import com.adammcneilly.pokedex.network.PokemonRepository
import com.adammcneilly.pokedex.testObserver
import com.adammcneilly.pokedex.whenever
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock

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