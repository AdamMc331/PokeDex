package com.adammcneilly.pokedex.detail

import android.annotation.SuppressLint
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.adammcneilly.pokedex.BaseObservableViewModel
import com.adammcneilly.pokedex.DispatcherProvider
import com.adammcneilly.pokedex.R
import com.adammcneilly.pokedex.models.Type
import com.adammcneilly.pokedex.network.PokemonRepository
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailActivityViewModel(
    private val repository: PokemonRepository,
    private val pokemonName: String,
    private val dispatcherProvider: DispatcherProvider = DispatcherProvider()
) : BaseObservableViewModel() {
    private val state = MutableLiveData<DetailActivityState>()

    private val currentState: DetailActivityState
        get() = state.value ?: DetailActivityState.Loading

    val title: String
        @SuppressLint("DefaultLocale")
        get() = pokemonName.capitalize()

    val toolbarColorRes: Int
        get() = (currentState as? DetailActivityState.Loaded)?.pokemon?.sortedTypes?.firstOrNull()?.getColorRes()
            ?: R.color.colorPrimary

    val toolbarTextColorRes: Int
        get() = (currentState as? DetailActivityState.Loaded)?.pokemon?.sortedTypes?.firstOrNull()?.getComplementaryColorRes()
            ?: R.color.mds_white

    val imageUrl: String
        get() = (currentState as? DetailActivityState.Loaded)?.pokemon?.sprites?.frontDefault.orEmpty()

    val showLoading: Boolean
        get() = currentState is DetailActivityState.Loading

    val showData: Boolean
        get() = (currentState as? DetailActivityState.Loaded)?.pokemon != null

    val showError: Boolean
        get() = currentState is DetailActivityState.Error

    val firstType: Type?
        get() = (currentState as? DetailActivityState.Loaded)?.pokemon?.sortedTypes?.firstOrNull()

    val secondType: Type?
        get() = (currentState as? DetailActivityState.Loaded)?.pokemon?.sortedTypes?.getOrNull(1)

    val showFirstType: Boolean
        get() = firstType != null

    val showSecondType: Boolean
        get() = secondType != null

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
                    DetailActivityState.Loaded(pokemon)
                } catch (error: Throwable) {
                    DetailActivityState.Error(error)
                }
            }

            setState(newState)
        }
    }

    private fun startLoading() {
        setState(DetailActivityState.Loading)
    }

    private fun setState(newState: DetailActivityState) {
        this.state.value = newState
        notifyChange()
    }
}