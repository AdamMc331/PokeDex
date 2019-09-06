package com.adammcneilly.pokedex.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.viewModelScope
import com.adammcneilly.pokedex.BaseObservableViewModel
import com.adammcneilly.pokedex.DispatcherProvider
import com.adammcneilly.pokedex.models.Pokemon
import com.adammcneilly.pokedex.network.PokemonRepository
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivityViewModel(
    private val repository: PokemonRepository,
    private val dispatcherProvider: DispatcherProvider = DispatcherProvider()
) : BaseObservableViewModel() {
    private val state = MutableLiveData<MainActivityState>().apply {
        value = MainActivityState.Loading
    }

    val pokemon: LiveData<List<Pokemon>> = Transformations.map(state) { state ->
        (state as? MainActivityState.Loaded)?.data?.results
    }

    val showLoading: LiveData<Boolean> = Transformations.map(state) { state ->
        state is MainActivityState.Loading
    }

    val showError: LiveData<Boolean> = Transformations.map(state) { state ->
        state is MainActivityState.Error
    }

    val showData: LiveData<Boolean> = Transformations.map(state) { state ->
        state is MainActivityState.Loaded
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
                    return@withContext MainActivityState.Loaded(response)
                } catch (error: Throwable) {
                    return@withContext MainActivityState.Error(error)
                }
            }

            setState(newState)
        }
    }

    private fun startLoading() {
        setState(MainActivityState.Loading)
    }

    private fun setState(newState: MainActivityState) {
        this.state.value = newState
        notifyChange()
    }
}