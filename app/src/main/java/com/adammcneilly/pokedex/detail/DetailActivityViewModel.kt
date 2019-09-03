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
        get() = state.value ?: DetailActivityState()

    val title: String
        @SuppressLint("DefaultLocale")
        get() = pokemonName.capitalize()

    val toolbarColorRes: Int
        get() = currentState.pokemon?.sortedTypes?.firstOrNull()?.getColorRes()
            ?: R.color.colorPrimary

    val toolbarTextColorRes: Int
        get() = currentState.pokemon?.sortedTypes?.firstOrNull()?.getComplementaryColorRes()
            ?: R.color.mds_white

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
        fetchPokemonDetail()
    }

    @Suppress("TooGenericExceptionCaught")
    private fun fetchPokemonDetail() {
        viewModelScope.launch {
            startLoading()

            val newState = withContext(dispatcherProvider.IO) {
                try {
                    val pokemon = repository.getPokemonDetail(pokemonName)
                    return@withContext currentState.copy(
                        loading = false,
                        pokemon = pokemon,
                        error = null
                    )
                } catch (error: Throwable) {
                    return@withContext currentState.copy(
                        loading = false,
                        pokemon = null,
                        error = error
                    )
                }
            }

            setState(newState)
        }
    }

    private fun startLoading() {
        val newState = currentState.copy(loading = true, pokemon = null, error = null)
        setState(newState)
    }

    private fun setState(newState: DetailActivityState) {
        this.state.value = newState
        notifyChange()
    }
}