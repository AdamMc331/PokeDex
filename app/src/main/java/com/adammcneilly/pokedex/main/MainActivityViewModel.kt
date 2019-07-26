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
    private val state = MutableLiveData<MainActivityState>()

    private val currentState: MainActivityState
        get() = state.value ?: MainActivityState()

    val pokemon: LiveData<List<Pokemon>> = Transformations.map(state) {
        it.data?.results
    }

    val showLoading: Boolean
        get() = currentState.loading

    val showError: Boolean
        get() = currentState.error != null

    val showData: Boolean
        get() = currentState.data != null

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
                    return@withContext currentState.copy(
                        loading = false,
                        data = response,
                        error = null
                    )
                } catch (error: Throwable) {
                    return@withContext currentState.copy(
                        loading = false,
                        data = null,
                        error = error
                    )
                }
            }

            setState(newState)
        }
    }

    private fun startLoading() {
        val newState = currentState.copy(loading = true, data = null, error = null)
        setState(newState)
    }

    private fun setState(newState: MainActivityState) {
        this.state.value = newState
        notifyChange()
    }
}