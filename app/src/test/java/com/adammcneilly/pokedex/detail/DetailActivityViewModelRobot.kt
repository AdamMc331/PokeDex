package com.adammcneilly.pokedex.detail

import com.adammcneilly.pokedex.DispatcherProvider
import com.adammcneilly.pokedex.models.Pokemon
import com.adammcneilly.pokedex.models.Type
import com.adammcneilly.pokedex.network.PokemonRepository
import com.adammcneilly.pokedex.whenever
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mockito.mock

class DetailActivityViewModelRobot(
    private val mockRepository: PokemonRepository = mock(PokemonRepository::class.java),
    private val dispatcherProvider: DispatcherProvider = DispatcherProvider(IO = Dispatchers.Unconfined, Main = Dispatchers.Unconfined),
    private var pokemonName: String = ""
) {
    private lateinit var viewModel: DetailActivityViewModel

    fun setInitialPokemonName(pokemonName: String) = apply {
        this.pokemonName = pokemonName
    }

    fun mockPokemonDetails(pokemonDetails: Pokemon) = apply {
        runBlocking {
            whenever(mockRepository.getPokemonDetail(anyString())).thenReturn(pokemonDetails)
        }
    }

    fun mockPokemonDetailsError(error: Throwable = IllegalArgumentException()) = apply {
        runBlocking {
            whenever(mockRepository.getPokemonDetail(anyString())).thenThrow(error)
        }
    }

    fun buildViewModel() = apply {
        this.viewModel = DetailActivityViewModel(
            repository = this.mockRepository,
            dispatcherProvider = this.dispatcherProvider,
            pokemonName = this.pokemonName
        )
    }

    fun assertShowLoading(showLoading: Boolean) = apply {
        assertEquals(showLoading, viewModel.showLoading)
    }

    fun assertShowData(showData: Boolean) = apply {
        assertEquals(showData, viewModel.showData)
    }

    fun assertShowError(showError: Boolean) = apply {
        assertEquals(showError, viewModel.showError)
    }

    fun assertTitle(title: String) = apply {
        assertEquals(title, viewModel.title)
    }

    fun assertToolbarColorRes(colorRes: Int) = apply {
        assertEquals(colorRes, viewModel.toolbarColorRes)
    }

    fun assertToolbarTextColorRes(textColorRes: Int) = apply {
        assertEquals(textColorRes, viewModel.toolbarTextColorRes)
    }

    fun assertFirstType(type: Type?) = apply {
        assertEquals(type, viewModel.firstType)
    }

    fun assertSecondType(type: Type?) = apply {
        assertEquals(type, viewModel.secondType)
    }

    fun assertShowFirstType(showFirstType: Boolean) = apply {
        assertEquals(showFirstType, viewModel.showFirstType)
    }

    fun assertShowSecondType(showSecondType: Boolean) = apply {
        assertEquals(showSecondType, viewModel.showSecondType)
    }
}