package com.adammcneilly.pokedex.detail

import com.adammcneilly.pokedex.DispatcherProvider
import com.adammcneilly.pokedex.data.PokemonRepository
import com.adammcneilly.pokedex.models.Pokemon
import com.adammcneilly.pokedex.models.Type
import com.adammcneilly.pokedex.testObserver
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import org.junit.Assert.assertEquals

class PokemonDetailViewModelRobot(
    private val mockRepository: PokemonRepository = mockk(),
    private val dispatcherProvider: DispatcherProvider = DispatcherProvider(
        IO = Dispatchers.Unconfined,
        Main = Dispatchers.Unconfined
    ),
    private var pokemonName: String = ""
) {
    private lateinit var viewModel: PokemonDetailViewModel

    fun setInitialPokemonName(pokemonName: String) = apply {
        this.pokemonName = pokemonName
    }

    fun mockPokemonDetails(pokemonDetails: Pokemon) = apply {
        coEvery { mockRepository.getPokemonDetail(any()) } returns pokemonDetails
    }

    fun mockPokemonDetailsError(error: Throwable = IllegalArgumentException()) = apply {
        coEvery { mockRepository.getPokemonDetail(any()) } throws error
    }

    fun buildViewModel() = apply {
        this.viewModel = PokemonDetailViewModel(
            repository = this.mockRepository,
            dispatcherProvider = this.dispatcherProvider,
            pokemonName = this.pokemonName
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

    fun assertTitle(title: String) = apply {
        assertEquals(title, viewModel.title)
    }

    fun assertToolbarColorRes(colorRes: Int) = apply {
        assertEquals(colorRes, viewModel.toolbarColorRes.testObserver().observedValue)
    }

    fun assertToolbarTextColorRes(textColorRes: Int) = apply {
        assertEquals(textColorRes, viewModel.toolbarTextColorRes.testObserver().observedValue)
    }

    fun assertFirstType(type: Type?) = apply {
        assertEquals(type, viewModel.firstType.testObserver().observedValue)
    }

    fun assertSecondType(type: Type?) = apply {
        assertEquals(type, viewModel.secondType.testObserver().observedValue)
    }

    fun assertShowFirstType(showFirstType: Boolean) = apply {
        assertEquals(showFirstType, viewModel.showFirstType.testObserver().observedValue)
    }

    fun assertShowSecondType(showSecondType: Boolean) = apply {
        assertEquals(showSecondType, viewModel.showSecondType.testObserver().observedValue)
    }
}