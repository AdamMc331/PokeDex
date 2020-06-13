package com.adammcneilly.pokedex.detail

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.viewModelScope
import com.adammcneilly.pokedex.BaseObservableViewModel
import com.adammcneilly.pokedex.R
import com.adammcneilly.pokedex.core.Pokemon
import com.adammcneilly.pokedex.core.Type
import com.adammcneilly.pokedex.data.PokemonRepository
import com.adammcneilly.pokedex.models.colorRes
import com.adammcneilly.pokedex.models.complimentaryColorRes
import com.adammcneilly.pokedex.views.ViewState
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

typealias PokemonDetailState = ViewState<Pokemon>

class PokemonDetailViewModel(
    private val repository: PokemonRepository,
    private val pokemonName: String
) : BaseObservableViewModel() {
    private val state = MutableLiveData<PokemonDetailState>().apply {
        value = ViewState.Loading()
    }

    val title: String
        @SuppressLint("DefaultLocale")
        get() = pokemonName.capitalize()

    private val pokemonDetail: LiveData<Pokemon> = Transformations.map(state) { state ->
        (state as? ViewState.Loaded)?.data
    }

    val firstType: LiveData<Type> = Transformations.map(pokemonDetail) { pokemon ->
        pokemon?.firstType
    }

    val secondType: LiveData<Type> = Transformations.map(pokemonDetail) { pokemon ->
        pokemon?.secondType
    }

    val toolbarColorRes: LiveData<Int> = Transformations.map(firstType) { firstType ->
        firstType?.colorRes() ?: R.color.colorPrimary
    }

    val toolbarTextColorRes: LiveData<Int> = Transformations.map(firstType) { firstType ->
        firstType?.complimentaryColorRes() ?: R.color.mds_white
    }

    val imageUrl: LiveData<String> = Transformations.map(pokemonDetail) { pokemon ->
        pokemon?.frontSpriteUrl.orEmpty()
    }

    val showLoading: LiveData<Boolean> = Transformations.map(state) { state ->
        state is ViewState.Loading
    }

    val showData: LiveData<Boolean> = Transformations.map(state) { state ->
        state is ViewState.Loaded
    }

    val showError: LiveData<Boolean> = Transformations.map(state) { state ->
        state is ViewState.Error
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

    private fun fetchPokemonDetail() {
        viewModelScope.launch {
            setState(ViewState.Loading())

            repository
                .getPokemonDetail(pokemonName)
                .map { result ->
                    result.toPokemonDetailState()
                }
                .collect { newState ->
                    setState(newState)
                }
        }
    }

    private fun setState(newState: PokemonDetailState) {
        this.state.value = newState
        notifyChange()
    }
}

private fun Result<Pokemon>.toPokemonDetailState(): PokemonDetailState {
    return when {
        this.isSuccess -> ViewState.Loaded(this.getOrThrow())
        else -> ViewState.Error(this.exceptionOrNull())
    }
}