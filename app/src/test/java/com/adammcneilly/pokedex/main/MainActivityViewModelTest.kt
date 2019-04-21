package com.adammcneilly.pokedex.main

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.adammcneilly.pokedex.DispatcherProvider
import com.adammcneilly.pokedex.models.Pokemon
import com.adammcneilly.pokedex.models.PokemonResponse
import com.adammcneilly.pokedex.network.PokemonAPI
import com.adammcneilly.pokedex.network.PokemonRepository
import com.adammcneilly.pokedex.testObserver
import com.adammcneilly.pokedex.whenever
import kotlinx.coroutines.Deferred
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

    private val mockAPI = mock(PokemonAPI::class.java)
    private val repository = PokemonRepository(mockAPI)
    private val testProvider = DispatcherProvider(IO = Dispatchers.Unconfined)

    @Test
    fun loadData() {
        runBlocking {
            val testResult = PokemonResponse(count = 10)

            val mockDeferred = mock(Deferred::class.java) as Deferred<PokemonResponse>
            whenever(mockDeferred.await()).thenReturn(testResult)
            whenever(mockAPI.getPokemonAsync()).thenReturn(mockDeferred)

            val viewModel = MainActivityViewModel(repository, testProvider)

            assertFalse(viewModel.showLoading)
            assertTrue(viewModel.showData)
            assertFalse(viewModel.showError)
        }
    }

    @Test
    fun loadError() {
        runBlocking {
            val mockDeferred = mock(Deferred::class.java) as Deferred<PokemonResponse>
            whenever(mockDeferred.await()).thenThrow(IllegalArgumentException())
            whenever(mockAPI.getPokemonAsync()).thenReturn(mockDeferred)

            val viewModel = MainActivityViewModel(repository, testProvider)

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

            val mockDeferred = mock(Deferred::class.java) as Deferred<PokemonResponse>
            whenever(mockDeferred.await()).thenReturn(testResult)
            whenever(mockAPI.getPokemonAsync()).thenReturn(mockDeferred)

            val viewModel = MainActivityViewModel(repository, testProvider)

            assertFalse(viewModel.showLoading)
            assertTrue(viewModel.showData)
            assertFalse(viewModel.showError)
            assertEquals(testPokemon, viewModel.pokemon.testObserver().observedValue)
        }
    }
}