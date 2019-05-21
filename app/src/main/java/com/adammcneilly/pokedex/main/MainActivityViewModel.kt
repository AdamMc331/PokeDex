package com.adammcneilly.pokedex.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.adammcneilly.pokedex.BaseObservableViewModel
import com.adammcneilly.pokedex.DispatcherProvider
import com.adammcneilly.pokedex.models.Pokemon
import com.adammcneilly.pokedex.data.PokemonRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivityViewModel(
    repository: PokemonRepository,
    dispatcherProvider: DispatcherProvider = DispatcherProvider()
) : BaseObservableViewModel() {
    private val state = MutableLiveData<MainActivityState>()

    private val currentState: MainActivityState
        get() = state.value ?: MainActivityState()

    private var job: Job? = null

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
        job = CoroutineScope(dispatcherProvider.IO).launch {
            withContext(dispatcherProvider.Main) {
                startLoading()
            }

            @Suppress("TooGenericExceptionCaught")
            val newState = try {
                val response = repository.getPokemon()
                currentState.copy(loading = false, data = response, error = null)
            } catch (error: Throwable) {
                currentState.copy(loading = false, data = null, error = error)
            }

            withContext(dispatcherProvider.Main) {
                setState(newState)
            }
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

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }
}