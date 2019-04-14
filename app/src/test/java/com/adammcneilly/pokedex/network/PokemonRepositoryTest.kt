package com.adammcneilly.pokedex.network

import com.adammcneilly.pokedex.models.PokemonResponse
import com.adammcneilly.pokedex.whenever
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import org.junit.Test
import org.mockito.Mockito.mock

class PokemonRepositoryTest {
    private val mockAPI = mock(PokemonAPI::class.java)
    private val repository = PokemonRepository(
        mockAPI,
        CompositeDisposable(),
        Schedulers.trampoline(),
        Schedulers.trampoline()
    )

    @Test
    fun loadData() {
        val testSub = repository.pokemonResponseState.test()

        whenever(mockAPI.getPokemon()).thenReturn(Single.just(PokemonResponse()))
        repository.fetchPokemon()

        testSub
            .assertValueCount(2)
            .assertValueAt(0) {
                it is NetworkState.Loading
            }
            .assertValueAt(1) {
                it is NetworkState.Loaded<*>
            }
    }

    @Test
    fun loadingError() {
        val testSub = repository.pokemonResponseState.test()

        whenever(mockAPI.getPokemon()).thenReturn(Single.error<PokemonResponse>(Throwable("Whoops")))
        repository.fetchPokemon()

        testSub
            .assertValueCount(2)
            .assertValueAt(0) {
                it is NetworkState.Loading
            }
            .assertValueAt(1) {
                it is NetworkState.Error
            }
    }
}