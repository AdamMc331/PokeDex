package com.adammcneilly.pokedex.pokemonlist

import com.adammcneilly.pokedex.DispatcherProvider
import com.adammcneilly.pokedex.core.Pokemon
import com.adammcneilly.pokedex.core.PokemonResponse
import com.adammcneilly.pokedex.data.PokemonRepository
import com.adammcneilly.pokedex.testObserver
import kotlin.coroutines.Continuation
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine
import kotlinx.coroutines.Dispatchers
import org.junit.Assert.assertEquals

class PokemonListViewModelRobot(
    private val testDispatcherProvider: DispatcherProvider = DispatcherProvider(
        IO = Dispatchers.Unconfined,
        Main = Dispatchers.Unconfined
    )
) {
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

private class FakeRepository : PokemonRepository {
    private lateinit var pokemonResponseContinuation: Continuation<PokemonResponse?>

    override suspend fun getPokemon(): PokemonResponse? {
        return suspendCoroutine { continuation ->
            pokemonResponseContinuation = continuation
        }
    }

    override suspend fun getPokemonDetail(pokemonName: String): Pokemon? {
        TODO("The function getPokemonDetail should not be called for this test case.")
    }

    fun mockPokemonResponse(response: PokemonResponse?) {
        pokemonResponseContinuation.resume(response)
    }

    fun mockPokemonResponseError(error: Throwable) {
        pokemonResponseContinuation.resumeWithException(error)
    }
}