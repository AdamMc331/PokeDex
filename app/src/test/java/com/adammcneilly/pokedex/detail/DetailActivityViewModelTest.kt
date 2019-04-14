package com.adammcneilly.pokedex.detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.adammcneilly.pokedex.models.Pokemon
import com.adammcneilly.pokedex.network.PokemonAPI
import com.adammcneilly.pokedex.network.PokemonRepository
import com.adammcneilly.pokedex.whenever
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mockito

class DetailActivityViewModelTest {
    @JvmField
    @Rule
    val instantTaskExecutor = InstantTaskExecutorRule()

    private val mockAPI = Mockito.mock(PokemonAPI::class.java)
    private val repository = PokemonRepository(
        mockAPI,
        CompositeDisposable(),
        Schedulers.trampoline(),
        Schedulers.trampoline()
    )

    @Test
    fun loadData() {
        whenever(mockAPI.getPokemonByName(anyString())).thenReturn(Single.just(Pokemon()))
        val viewModel = DetailActivityViewModel(repository, "")

        Assert.assertFalse(viewModel.showLoading)
        Assert.assertTrue(viewModel.showData)
        Assert.assertFalse(viewModel.showError)
    }

    @Test
    fun loadError() {
        whenever(mockAPI.getPokemonByName(anyString())).thenReturn(Single.error<Pokemon>(Throwable("Whoops")))
        val viewModel = DetailActivityViewModel(repository, "")

        Assert.assertFalse(viewModel.showLoading)
        Assert.assertFalse(viewModel.showData)
        Assert.assertTrue(viewModel.showError)
    }

    @Test
    fun getPokemon() {
        val testPokemon = Pokemon(name = "Adam")
        whenever(mockAPI.getPokemonByName(anyString())).thenReturn(Single.just(testPokemon))
        val viewModel = DetailActivityViewModel(repository, testPokemon.name.orEmpty())

        Assert.assertFalse(viewModel.showLoading)
        Assert.assertTrue(viewModel.showData)
        Assert.assertFalse(viewModel.showError)
        Assert.assertEquals(testPokemon.name?.capitalize(), viewModel.title)
    }
}