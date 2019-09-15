package com.adammcneilly.pokedex.pokemonlist

import com.adammcneilly.pokedex.DispatcherProvider
import com.adammcneilly.pokedex.data.PokemonRepository
import com.adammcneilly.pokedex.models.Pokemon
import com.adammcneilly.pokedex.models.PokemonResponse
import com.adammcneilly.pokedex.testObserver
import com.adammcneilly.pokedex.whenever
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.mockito.Mockito.mock

class PokemonListViewModelRobot(
    private val mockRepository: PokemonRepository = mock(PokemonRepository::class.java),
    private val testDispatcherProvider: DispatcherProvider = DispatcherProvider(
        IO = Dispatchers.Unconfined,
        Main = Dispatchers.Unconfined
    )
) {
    private lateinit var viewModel: PokemonListViewModel

    fun mockPokemonResponse(response: PokemonResponse) = apply {
        runBlocking {
            whenever(mockRepository.getPokemon()).thenReturn(response)
        }
    }

    fun mockPokemonResponseError(error: Throwable = IllegalArgumentException()) = apply {
        runBlocking {
            whenever(mockRepository.getPokemon()).thenThrow(error)
        }
    }

    fun buildViewModel() = apply {
        viewModel = PokemonListViewModel(
            mockRepository,
            testDispatcherProvider
        )
    }

    fun assertShowLoading(showLoading: Boolean) = apply {
        assertEquals(showLoading, viewModel.showLoading.testObserver().observedValue)
    }

    fun assertShowData(showData: Boolean) = apply {
        assertEquals(showData, viewModel.showData.testObserver().observedValue)
    }

    fun assertShowError(showError: Boolean) = apply {
        assertEquals(showError, viewModel.showError.testObserver().observedValue)
    }

    fun assertPokemonList(pokemonList: List<Pokemon>) = apply {
        assertEquals(pokemonList, viewModel.pokemon.testObserver().observedValue)
    }
}