package com.adammcneilly.pokedex.main

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.adammcneilly.pokedex.models.Pokemon
import com.adammcneilly.pokedex.models.PokemonResponse
import com.adammcneilly.pokedex.network.PokemonAPI
import com.adammcneilly.pokedex.network.PokemonRepository
import com.adammcneilly.pokedex.whenever
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock


class MainActivityViewModelTest {
    @JvmField
    @Rule
    val instantTaskExecutor = InstantTaskExecutorRule()

    private val mockAPI = mock(PokemonAPI::class.java)
    private val repository = PokemonRepository(
        mockAPI,
        CompositeDisposable(),
        Schedulers.trampoline(),
        Schedulers.trampoline()
    )

    @Test
    fun loadData() {
        whenever(mockAPI.getPokemon()).thenReturn(Single.just(PokemonResponse()))
        val viewModel = MainActivityViewModel(repository)

        assertFalse(viewModel.showLoading)
        assertTrue(viewModel.showData)
        assertFalse(viewModel.showError)
    }

    @Test
    fun loadError() {
        whenever(mockAPI.getPokemon()).thenReturn(Single.error<PokemonResponse>(Throwable("Whoops")))
        val viewModel = MainActivityViewModel(repository)

        assertFalse(viewModel.showLoading)
        assertFalse(viewModel.showData)
        assertTrue(viewModel.showError)
    }

    @Test
    fun getPokemon() {
        val testPokemon = listOf(Pokemon(name = "Adam"))
        whenever(mockAPI.getPokemon()).thenReturn(Single.just(PokemonResponse(results = testPokemon)))
        val viewModel = MainActivityViewModel(repository)

        assertFalse(viewModel.showLoading)
        assertTrue(viewModel.showData)
        assertFalse(viewModel.showError)
        assertEquals(testPokemon, viewModel.pokemon.value)
    }
}