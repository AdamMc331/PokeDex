package com.adammcneilly.pokedex.detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.adammcneilly.pokedex.DispatcherProvider
import com.adammcneilly.pokedex.R
import com.adammcneilly.pokedex.models.Pokemon
import com.adammcneilly.pokedex.models.Type
import com.adammcneilly.pokedex.models.TypeSlot
import com.adammcneilly.pokedex.network.PokemonAPI
import com.adammcneilly.pokedex.network.PokemonRepository
import com.adammcneilly.pokedex.whenever
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mockito.mock

@Suppress("UNCHECKED_CAST")
class DetailActivityViewModelTest {
    @JvmField
    @Rule
    val instantTaskExecutor = InstantTaskExecutorRule()

    private val mockAPI = mock(PokemonAPI::class.java)
    private val repository = PokemonRepository(mockAPI)
    private val testProvider = DispatcherProvider(IO = Dispatchers.Unconfined, Main = Dispatchers.Unconfined)

    @Test
    fun loadData() {
        runBlocking {
            val testPokemon = Pokemon(name = "Adam", types = listOf(TypeSlot(type = Type("grass"))))

            val mockDeferred = mock(Deferred::class.java) as Deferred<Pokemon>
            whenever(mockDeferred.await()).thenReturn(testPokemon)

            whenever(mockAPI.getPokemonDetailAsync(anyString())).thenReturn(mockDeferred)
            val viewModel = DetailActivityViewModel(repository, testPokemon.name.orEmpty(), testProvider)

            assertFalse(viewModel.showLoading)
            assertTrue(viewModel.showData)
            assertFalse(viewModel.showError)
            assertEquals(testPokemon.name?.capitalize(), viewModel.title)
            assertEquals(R.color.type_grass, viewModel.toolbarColorRes)
            assertEquals(R.color.mds_white, viewModel.toolbarTextColorRes)
        }
    }

    @Test
    fun getTypesWithNoTypes() {
        runBlocking {
            val testPokemon = Pokemon()

            val mockDeferred = mock(Deferred::class.java) as Deferred<Pokemon>
            whenever(mockDeferred.await()).thenReturn(testPokemon)

            whenever(mockAPI.getPokemonDetailAsync(anyString())).thenReturn(mockDeferred)
            val viewModel = DetailActivityViewModel(repository, testPokemon.name.orEmpty(), testProvider)

            assertNull(viewModel.firstType)
            assertNull(viewModel.secondType)
            assertFalse(viewModel.showFirstType)
            assertFalse(viewModel.showSecondType)
        }
    }

    @Test
    fun getTypesWithOneType() {
        runBlocking {
            val firstType = Type("grass", "grassurl")
            val testPokemon = Pokemon(
                types = listOf(TypeSlot(slot = 1, type = firstType))
            )

            val mockDeferred = mock(Deferred::class.java) as Deferred<Pokemon>
            whenever(mockDeferred.await()).thenReturn(testPokemon)

            whenever(mockAPI.getPokemonDetailAsync(anyString())).thenReturn(mockDeferred)
            val viewModel = DetailActivityViewModel(repository, testPokemon.name.orEmpty(), testProvider)

            assertEquals(firstType, viewModel.firstType)
            assertNull(viewModel.secondType)
            assertTrue(viewModel.showFirstType)
            assertFalse(viewModel.showSecondType)
        }
    }

    @Test
    fun getTypesWithTwoTypes() {
        runBlocking {
            val firstType = Type("grass", "grassurl")
            val secondType = Type("bug", "bugurl")
            val testPokemon = Pokemon(
                types = listOf(TypeSlot(slot = 1, type = firstType), TypeSlot(slot = 2, type = secondType))
            )

            val mockDeferred = mock(Deferred::class.java) as Deferred<Pokemon>
            whenever(mockDeferred.await()).thenReturn(testPokemon)

            whenever(mockAPI.getPokemonDetailAsync(anyString())).thenReturn(mockDeferred)
            val viewModel = DetailActivityViewModel(repository, testPokemon.name.orEmpty(), testProvider)

            assertEquals(firstType, viewModel.firstType)
            assertEquals(secondType, viewModel.secondType)
            assertTrue(viewModel.showFirstType)
            assertTrue(viewModel.showSecondType)
        }
    }

    @Test
    fun loadError() {
        runBlocking {
            val mockDeferred = mock(Deferred::class.java) as Deferred<Pokemon>
            whenever(mockDeferred.await()).thenThrow(IllegalArgumentException())

            whenever(mockAPI.getPokemonDetailAsync(anyString())).thenReturn(mockDeferred)
            val viewModel = DetailActivityViewModel(repository, "", testProvider)

            assertFalse(viewModel.showLoading)
            assertFalse(viewModel.showData)
            assertTrue(viewModel.showError)
        }
    }
}