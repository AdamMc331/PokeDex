package com.adammcneilly.pokedex.pokemonlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.viewModelScope
import com.adammcneilly.pokedex.BaseObservableViewModel
import com.adammcneilly.pokedex.DispatcherProvider
import com.adammcneilly.pokedex.data.PokemonRepository
import com.adammcneilly.pokedex.models.Pokemon
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PokemonListViewModel(
    private val repository: PokemonRepository,
    private val dispatcherProvider: DispatcherProvider = DispatcherProvider()
) : BaseObservableViewModel() {
    private val state = MutableLiveData<PokemonListState>().apply {
        value = PokemonListState.Loading
    }

    val pokemon: LiveData<List<Pokemon>> = Transformations.map(state) { state ->
        (state as? PokemonListState.Loaded)?.data?.results
    }

    val showLoading: LiveData<Boolean> = Transformations.map(state) { state ->
        state is PokemonListState.Loading
    }

    val showError: LiveData<Boolean> = Transformations.map(state) { state ->
        state is PokemonListState.Error
    }

    val showData: LiveData<Boolean> = Transformations.map(state) { state ->
        state is PokemonListState.Loaded
    }

    init {
        fetchPokemonList()
    }

    @Suppress("TooGenericExceptionCaught")
    private fun fetchPokemonList() {
        viewModelScope.launch {
            startLoading()

            val newState = withContext(dispatcherProvider.IO) {
                try {
                    val response = repository.getPokemon()
                    return@withContext PokemonListState.Loaded(
                        response
                    )
                } catch (error: Throwable) {
                    return@withContext PokemonListState.Error(
                        error
                    )
                }
            }

            setState(newState)
        }
    }

    private fun startLoading() {
        setState(PokemonListState.Loading)
    }

    private fun setState(newState: PokemonListState) {
        this.state.value = newState
        notifyChange()
    }
}