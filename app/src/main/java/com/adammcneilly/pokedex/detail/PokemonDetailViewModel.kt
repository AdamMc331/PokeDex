package com.adammcneilly.pokedex.detail

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.viewModelScope
import com.adammcneilly.pokedex.BaseObservableViewModel
import com.adammcneilly.pokedex.DispatcherProvider
import com.adammcneilly.pokedex.R
import com.adammcneilly.pokedex.models.Type
import com.adammcneilly.pokedex.network.PokemonRepository
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PokemonDetailViewModel(
    private val repository: PokemonRepository,
    private val pokemonName: String,
    private val dispatcherProvider: DispatcherProvider = DispatcherProvider()
) : BaseObservableViewModel() {
    private val state = MutableLiveData<PokemonDetailState>().apply {
        value = PokemonDetailState.Loading
    }

    val title: String
        @SuppressLint("DefaultLocale")
        get() = pokemonName.capitalize()

    val firstType: LiveData<Type> = Transformations.map(state) { state ->
        (state as? PokemonDetailState.Loaded)?.pokemon?.sortedTypes?.firstOrNull()
    }

    val secondType: LiveData<Type> = Transformations.map(state) { state ->
        (state as? PokemonDetailState.Loaded)?.pokemon?.sortedTypes?.getOrNull(1)
    }

    val toolbarColorRes: LiveData<Int> = Transformations.map(firstType) { firstType ->
        firstType?.getColorRes() ?: R.color.colorPrimary
    }

    val toolbarTextColorRes: LiveData<Int> = Transformations.map(firstType) { firstType ->
        firstType?.getComplementaryColorRes() ?: R.color.mds_white
    }

    val imageUrl: LiveData<String> = Transformations.map(state) { state ->
        (state as? PokemonDetailState.Loaded)?.pokemon?.sprites?.frontDefault.orEmpty()
    }

    val showLoading: LiveData<Boolean> = Transformations.map(state) { state ->
        state is PokemonDetailState.Loading
    }

    val showData: LiveData<Boolean> = Transformations.map(state) { state ->
        state is PokemonDetailState.Loaded
    }

    val showError: LiveData<Boolean> = Transformations.map(state) { state ->
        state is PokemonDetailState.Error
    }

    val showFirstType: LiveData<Boolean> = Transformations.map(firstType) { type ->
        type != null
    }

    val showSecondType: LiveData<Boolean> = Transformations.map(secondType) { type ->
        type != null
    }

    init {
        fetchPokemonDetail()
    }

    @Suppress("TooGenericExceptionCaught")
    private fun fetchPokemonDetail() {
        viewModelScope.launch {
            startLoading()

            val newState = withContext(dispatcherProvider.IO) {
                return@withContext try {
                    val pokemon = repository.getPokemonDetail(pokemonName)
                    PokemonDetailState.Loaded(pokemon)
                } catch (error: Throwable) {
                    PokemonDetailState.Error(error)
                }
            }

            setState(newState)
        }
    }

    private fun startLoading() {
        setState(PokemonDetailState.Loading)
    }

    private fun setState(newState: PokemonDetailState) {
        this.state.value = newState
        notifyChange()
    }
}