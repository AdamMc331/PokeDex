package com.adammcneilly.pokedex.detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.adammcneilly.pokedex.R
import com.adammcneilly.pokedex.models.*
import com.adammcneilly.pokedex.network.PokemonAPI
import com.adammcneilly.pokedex.network.PokemonRepository
import com.adammcneilly.pokedex.whenever
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import org.junit.Assert.*
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
        val testPokemon = Pokemon(name = "Adam", types = listOf(TypeSlot(type = Type("grass"))))
        val testSpecies = Species(color = Color("green"))

        whenever(mockAPI.getPokemonByName(anyString())).thenReturn(Single.just(testPokemon))
        whenever(mockAPI.getPokemonSpecies(anyString())).thenReturn(Single.just(testSpecies))
        val viewModel = DetailActivityViewModel(repository, testPokemon.name.orEmpty())

        assertFalse(viewModel.showLoading)
        assertTrue(viewModel.showData)
        assertFalse(viewModel.showError)
        assertEquals(testPokemon.name?.capitalize(), viewModel.title)
        assertEquals(R.color.type_grass, viewModel.toolbarColorRes)
        assertEquals(R.color.mds_white, viewModel.toolbarTextColorRes)
    }

    @Test
    fun getTypesWithNoTypes() {
        whenever(mockAPI.getPokemonByName(anyString())).thenReturn(Single.just(Pokemon()))
        whenever(mockAPI.getPokemonSpecies(anyString())).thenReturn(Single.just(Species()))
        val viewModel = DetailActivityViewModel(repository, "")

        assertNull(viewModel.firstType)
        assertNull(viewModel.secondType)
        assertFalse(viewModel.showFirstType)
        assertFalse(viewModel.showSecondType)
    }

    @Test
    fun getTypesWithOneType() {
        val firstType = Type("grass", "grassurl")
        val testPokemon = Pokemon(
            types = listOf(TypeSlot(slot = 1, type = firstType))
        )

        whenever(mockAPI.getPokemonByName(anyString())).thenReturn(Single.just(testPokemon))
        whenever(mockAPI.getPokemonSpecies(anyString())).thenReturn(Single.just(Species()))
        val viewModel = DetailActivityViewModel(repository, testPokemon.name.orEmpty())

        assertEquals(firstType, viewModel.firstType)
        assertNull(viewModel.secondType)
        assertTrue(viewModel.showFirstType)
        assertFalse(viewModel.showSecondType)
    }

    @Test
    fun getTypesWithTwoTypes() {
        val firstType = Type("grass", "grassurl")
        val secondType = Type("bug", "bugurl")
        val testPokemon = Pokemon(
            types = listOf(TypeSlot(slot = 1, type = firstType), TypeSlot(slot = 2, type = secondType))
        )

        whenever(mockAPI.getPokemonByName(anyString())).thenReturn(Single.just(testPokemon))
        whenever(mockAPI.getPokemonSpecies(anyString())).thenReturn(Single.just(Species()))
        val viewModel = DetailActivityViewModel(repository, testPokemon.name.orEmpty())

        assertEquals(firstType, viewModel.firstType)
        assertEquals(secondType, viewModel.secondType)
        assertTrue(viewModel.showFirstType)
        assertTrue(viewModel.showSecondType)
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