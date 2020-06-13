package com.adammcneilly.pokedex.pokemonlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.viewModelScope
import com.adammcneilly.pokedex.BaseObservableViewModel
import com.adammcneilly.pokedex.core.Pokemon
import com.adammcneilly.pokedex.core.PokemonResponse
import com.adammcneilly.pokedex.data.PokemonRepository
import com.adammcneilly.pokedex.views.ViewState
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

typealias PokemonListState = ViewState<PokemonResponse>

class PokemonListViewModel(
    private val repository: PokemonRepository
) : BaseObservableViewModel() {
    private val state = MutableLiveData<PokemonListState>().apply {
        value = ViewState.Loading()
    }

    val pokemon: LiveData<List<Pokemon>> = Transformations.map(state) { state ->
        (state as? ViewState.Loaded)?.data?.results
    }

    val showLoading: LiveData<Boolean> = Transformations.map(state) { state ->
        state is ViewState.Loading
    }

    val showError: LiveData<Boolean> = Transformations.map(state) { state ->
        state is ViewState.Error
    }

    val showData: LiveData<Boolean> = Transformations.map(state) { state ->
        state is ViewState.Loaded
    }

    init {
        fetchPokemonList()
    }

    @Suppress("TooGenericExceptionCaught")
    private fun fetchPokemonList() {
        viewModelScope.launch {
            setState(ViewState.Loading())

            repository
                .getPokemon()
                .map { result ->
                    result.toPokemonListState()
                }
                .collect { newState ->
                    setState(newState)
                }
        }
    }

    private fun setState(newState: PokemonListState) {
        this.state.value = newState
        notifyChange()
    }
}

private fun Result<PokemonResponse>.toPokemonListState(): PokemonListState {
    return when {
        this.isSuccess -> ViewState.Loaded(this.getOrThrow())
        else -> ViewState.Error(this.exceptionOrNull())
    }
}