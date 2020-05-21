package com.adammcneilly.pokedex.pokemonlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.viewModelScope
import com.adammcneilly.pokedex.BaseObservableViewModel
import com.adammcneilly.pokedex.core.Pokemon
import com.adammcneilly.pokedex.core.PokemonResponse
import com.adammcneilly.pokedex.data.PokemonRepository
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class PokemonListViewModel(
    private val repository: PokemonRepository
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
            setState(PokemonListState.Loading)

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
        this.isSuccess -> PokemonListState.Loaded(this.getOrThrow())
        else -> PokemonListState.Error(this.exceptionOrNull())
    }
}