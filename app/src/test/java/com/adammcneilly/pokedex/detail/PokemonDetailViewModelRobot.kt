package com.adammcneilly.pokedex.detail

import com.adammcneilly.pokedex.core.Pokemon
import com.adammcneilly.pokedex.core.PokemonResponse
import com.adammcneilly.pokedex.core.Type
import com.adammcneilly.pokedex.data.PokemonRepository
import com.adammcneilly.pokedex.testObserver
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals

class PokemonDetailViewModelRobot(
    private var pokemonName: String = ""
) {
    private val mockRepository = FakeRepository()
    private lateinit var viewModel: PokemonDetailViewModel

    fun setInitialPokemonName(pokemonName: String) = apply {
        this.pokemonName = pokemonName
    }

    fun mockPokemonDetails(pokemonDetails: Pokemon) = apply {
        mockRepository.mockPokemonDetail(pokemonDetails)
    }

    fun mockPokemonDetailsError(error: Throwable = IllegalArgumentException()) = apply {
        mockRepository.mockPokemonDetailError(error)
    }

    fun buildViewModel() = apply {
        this.viewModel = PokemonDetailViewModel(
            repository = this.mockRepository,
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

/**
 * This is a fake implementation of a [PokemonRepository] that gives us greater control over the
 * responses and timing of responses from the repository.
 */
private class FakeRepository : PokemonRepository {
    private val pokemonDetailChannel = Channel<Result<Pokemon>>()

    override suspend fun getPokemon(): PokemonResponse? {
        TODO("The function getPokemon should not be called for this test case.")
    }

    override fun getPokemonDetail(pokemonName: String): Flow<Result<Pokemon>> {
        return pokemonDetailChannel.consumeAsFlow()
    }

    fun mockPokemonDetail(detail: Pokemon) {
        runBlocking {
            pokemonDetailChannel.send(Result.success(detail))
        }
    }

    fun mockPokemonDetailError(error: Throwable) {
        runBlocking {
            pokemonDetailChannel.send(Result.failure(error))
        }
    }
}