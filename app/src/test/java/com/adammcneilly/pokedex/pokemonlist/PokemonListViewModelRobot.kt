package com.adammcneilly.pokedex.pokemonlist

import com.adammcneilly.pokedex.core.Pokemon
import com.adammcneilly.pokedex.core.PokemonResponse
import com.adammcneilly.pokedex.data.PokemonRepository
import com.adammcneilly.pokedex.testObserver
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals

class PokemonListViewModelRobot {
    private val mockRepository = FakeRepository()
    private lateinit var viewModel: PokemonListViewModel

    fun mockPokemonResponse(response: PokemonResponse) = apply {
        mockRepository.mockPokemonResponse(response)
    }

    fun mockPokemonResponseError(error: Throwable = IllegalArgumentException()) = apply {
        mockRepository.mockPokemonResponseError(error)
    }

    fun buildViewModel() = apply {
        viewModel = PokemonListViewModel(
            mockRepository
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

private class FakeRepository : PokemonRepository {
    private val pokemonResponseChannel = Channel<Result<PokemonResponse>>()

    override fun getPokemon(): Flow<Result<PokemonResponse>> {
        return pokemonResponseChannel.consumeAsFlow()
    }

    override fun getPokemonDetail(pokemonName: String): Flow<Result<Pokemon>> {
        TODO("The function getPokemonDetail should not be called for this test case.")
    }

    fun mockPokemonResponse(response: PokemonResponse) {
        runBlocking {
            pokemonResponseChannel.send(Result.success(response))
        }
    }

    fun mockPokemonResponseError(error: Throwable) {
        runBlocking {
            pokemonResponseChannel.send(Result.failure(error))
        }
    }
}