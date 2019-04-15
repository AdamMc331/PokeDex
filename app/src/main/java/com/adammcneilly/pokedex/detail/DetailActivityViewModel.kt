package com.adammcneilly.pokedex.detail

import androidx.lifecycle.MutableLiveData
import com.adammcneilly.pokedex.BaseObservableViewModel
import com.adammcneilly.pokedex.network.NetworkState
import com.adammcneilly.pokedex.network.PokemonRepository
import io.reactivex.disposables.CompositeDisposable

class DetailActivityViewModel(
    repository: PokemonRepository,
    private val pokemonName: String
) : BaseObservableViewModel() {
    private val compositeDisposable = CompositeDisposable()
    private val state = MutableLiveData<NetworkState>()

    val title: String
        get() = pokemonName.capitalize()

    val showLoading: Boolean
        get() = state.value == null || state.value == NetworkState.Loading

    val showData: Boolean
        get() = state.value is NetworkState.Loaded<*>

    val showError: Boolean
        get() = state.value is NetworkState.Error

    init {
        compositeDisposable.add(repository.pokemonState.subscribe(this::publishState))

        repository.fetchPokemonByName(pokemonName)
    }

    private fun publishState(newState: NetworkState) {
        this.state.value = newState
        notifyChange()
    }
}