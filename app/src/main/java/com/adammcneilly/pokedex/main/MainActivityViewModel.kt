package com.adammcneilly.pokedex.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.adammcneilly.pokedex.BaseObservableViewModel
import com.adammcneilly.pokedex.models.Pokemon
import com.adammcneilly.pokedex.models.PokemonResponse
import com.adammcneilly.pokedex.network.NetworkState
import com.adammcneilly.pokedex.network.PokemonRepository
import io.reactivex.disposables.CompositeDisposable

class MainActivityViewModel(
    repository: PokemonRepository
) : BaseObservableViewModel() {
    private val compositeDisposable = CompositeDisposable()
    private val state = MutableLiveData<NetworkState>()

    val pokemon: LiveData<List<Pokemon>> = Transformations.map(state) {
        val data = (it as? NetworkState.Loaded<*>)?.data as? PokemonResponse
        data?.results
    }

    val showLoading: Boolean
        get() = state.value == null || state.value is NetworkState.Loading

    val showError: Boolean
        get() = state.value is NetworkState.Error

    val showData: Boolean
        get() = state.value is NetworkState.Loaded<*>

    init {
        compositeDisposable.add(repository.pokemonResponseState.subscribe(this::publishState))

        repository.fetchPokemon()
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }

    private fun publishState(newState: NetworkState) {
        this.state.value = newState
        notifyChange()
    }
}