package com.adammcneilly.pokedex.detail

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.adammcneilly.pokedex.BaseObservableViewModel
import com.adammcneilly.pokedex.R
import com.adammcneilly.pokedex.core.Pokemon
import com.adammcneilly.pokedex.core.Type
import com.adammcneilly.pokedex.data.PokemonRepository
import com.adammcneilly.pokedex.models.colorRes
import com.adammcneilly.pokedex.models.complimentaryColorRes
import com.adammcneilly.pokedex.redux.Store
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
class PokemonDetailViewModel(
    private val repository: PokemonRepository,
    private val pokemonName: String,
    store: Store<PokemonDetailState>
) : BaseObservableViewModel() {

    private val state: LiveData<PokemonDetailState> = store.state.asLiveData()

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
        val fetchAction = PokemonDetailAction.FetchPokemon(
            pokemonName = pokemonName,
            requestScope = viewModelScope
        )
        store.dispatch(fetchAction)
    }
}