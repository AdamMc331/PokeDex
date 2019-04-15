package com.adammcneilly.pokedex.detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.adammcneilly.pokedex.R
import com.adammcneilly.pokedex.models.Color
import com.adammcneilly.pokedex.models.Pokemon
import com.adammcneilly.pokedex.models.Species
import com.adammcneilly.pokedex.network.PokemonAPI
import com.adammcneilly.pokedex.network.PokemonRepository
import com.adammcneilly.pokedex.whenever
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import org.junit.Assert.assertTrue
import org.junit.Assert.assertFalse
import org.junit.Assert.assertEquals
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
        val testPokemon = Pokemon(name = "Adam")
        val testSpecies = Species(color = Color("green"))

        whenever(mockAPI.getPokemonByName(anyString())).thenReturn(Single.just(testPokemon))
        whenever(mockAPI.getPokemonSpecies(anyString())).thenReturn(Single.just(testSpecies))
        val viewModel = DetailActivityViewModel(repository, testPokemon.name.orEmpty())

        assertFalse(viewModel.showLoading)
        assertTrue(viewModel.showData)
        assertFalse(viewModel.showError)
        assertEquals(testPokemon.name?.capitalize(), viewModel.title)
        assertEquals(R.color.mds_green_500, viewModel.toolbarColorRes)
        assertEquals(R.color.mds_white, viewModel.toolbarTextColorRes)
    }

    @Test
    fun loadError() {
        whenever(mockAPI.getPokemonByName(anyString())).thenReturn(Single.error<Pokemon>(Throwable("Whoops")))
        val viewModel = DetailActivityViewModel(repository, "")

        assertFalse(viewModel.showLoading)
        assertFalse(viewModel.showData)
        assertTrue(viewModel.showError)
    }
}