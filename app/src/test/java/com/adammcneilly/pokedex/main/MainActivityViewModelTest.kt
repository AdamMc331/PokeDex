package com.adammcneilly.pokedex.main

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.adammcneilly.pokedex.DispatcherProvider
import com.adammcneilly.pokedex.models.Pokemon
import com.adammcneilly.pokedex.models.PokemonResponse
import com.adammcneilly.pokedex.data.PokemonRepository
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

    private val mockRepository = mock(PokemonRepository::class.java)
    private val testProvider = DispatcherProvider(IO = Dispatchers.Unconfined, Main = Dispatchers.Unconfined)

    @Test
    fun loadData() {
        runBlocking {
            val testResult = PokemonResponse(count = 10)

            whenever(mockRepository.getPokemon()).thenReturn(testResult)

            val viewModel = MainActivityViewModel(mockRepository, testProvider)

            assertFalse(viewModel.showLoading)
            assertTrue(viewModel.showData)
            assertFalse(viewModel.showError)
        }
    }

    @Test
    fun loadError() {
        runBlocking {
            whenever(mockRepository.getPokemon()).thenThrow(IllegalArgumentException())

            val viewModel = MainActivityViewModel(mockRepository, testProvider)

            assertFalse(viewModel.showLoading)
            assertFalse(viewModel.showData)
            assertTrue(viewModel.showError)
        }
    }

    @Test
    fun getPokemon() {
        runBlocking {
            val testPokemon = listOf(Pokemon(name = "Adam"))
            val testResult = PokemonResponse(results = testPokemon)

            whenever(mockRepository.getPokemon()).thenReturn(testResult)

            val viewModel = MainActivityViewModel(mockRepository, testProvider)

            assertFalse(viewModel.showLoading)
            assertTrue(viewModel.showData)
            assertFalse(viewModel.showError)
            assertEquals(testPokemon, viewModel.pokemon.testObserver().observedValue)
        }
    }
}