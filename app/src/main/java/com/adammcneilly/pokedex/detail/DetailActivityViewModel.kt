package com.adammcneilly.pokedex.detail

import androidx.lifecycle.MutableLiveData
import com.adammcneilly.pokedex.BaseObservableViewModel
import com.adammcneilly.pokedex.R
import com.adammcneilly.pokedex.models.Pokemon
import com.adammcneilly.pokedex.models.Species
import com.adammcneilly.pokedex.models.Type
import com.adammcneilly.pokedex.network.NetworkState
import com.adammcneilly.pokedex.network.PokemonRepository
import io.reactivex.disposables.CompositeDisposable

class DetailActivityViewModel(
    private val repository: PokemonRepository,
    private val pokemonName: String
) : BaseObservableViewModel() {
    private val compositeDisposable = CompositeDisposable()
    private val state = MutableLiveData<DetailActivityState>()

    private val currentState: DetailActivityState
        get() = state.value ?: DetailActivityState()

    val title: String
        get() = pokemonName.capitalize()

    val toolbarColorRes: Int
        get() = currentState.species?.color?.toColorRes() ?: R.color.colorPrimary

    val toolbarTextColorRes: Int
        get() = currentState.species?.color?.getComplementaryColorRes() ?: R.color.mds_white

    val imageUrl: String
        get() = currentState.pokemon?.sprites?.frontDefault.orEmpty()

    val showLoading: Boolean
        get() = currentState.loading

    val showData: Boolean
        get() = currentState.pokemon != null

    val showError: Boolean
        get() = currentState.error != null

    val firstType: Type?
        get() = currentState.pokemon?.sortedTypes?.firstOrNull()

    val secondType: Type?
        get() = currentState.pokemon?.sortedTypes?.getOrNull(1)

    val showFirstType: Boolean
        get() = firstType != null

    val showSecondType: Boolean
        get() = secondType != null

    init {
        compositeDisposable.add(repository.pokemonState.subscribe(this::processPokemonState))
        compositeDisposable.add(repository.pokemonSpecies.subscribe(this::processSpecies))

        repository.fetchPokemonByName(pokemonName)
    }

    private fun processPokemonState(networkState: NetworkState) {
        val newState: DetailActivityState = when (networkState) {
            NetworkState.Loading -> currentState.copy(loading = true, pokemon = null, error = null)
            is NetworkState.Loaded<*> -> {
                repository.fetchPokemonSpecies(pokemonName)
                currentState.copy(loading = false, pokemon = networkState.data as? Pokemon, error = null)
            }
            is NetworkState.Error -> currentState.copy(loading = false, pokemon = null, error = networkState.error)
        }

        this.state.value = newState
        notifyChange()
    }

    private fun processSpecies(networkState: NetworkState) {
        val newState: DetailActivityState = when (networkState) {
            is NetworkState.Loaded<*> -> currentState.copy(species = networkState.data as? Species)
            else -> currentState
        }

        this.state.value = newState
        notifyChange()
    }
}