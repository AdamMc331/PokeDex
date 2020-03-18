package com.adammcneilly.pokedex.detail

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.viewModelScope
import com.adammcneilly.pokedex.BaseObservableViewModel
import com.adammcneilly.pokedex.DispatcherProvider
import com.adammcneilly.pokedex.R
import com.adammcneilly.pokedex.core.Pokemon
import com.adammcneilly.pokedex.core.Type
import com.adammcneilly.pokedex.data.PokemonRepository
import com.adammcneilly.pokedex.models.colorRes
import com.adammcneilly.pokedex.models.complimentaryColorRes
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

    private val pokemonDetail: LiveData<Pokemon> = Transformations.map(state) { state ->
        (state as? PokemonDetailState.Loaded)?.pokemon
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
                    if (pokemon != null) {
                        PokemonDetailState.Loaded(pokemon)
                    } else {
                        PokemonDetailState.Error(
                            Throwable("Unable to fetch Pokemon details.")
                        )
                    }
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