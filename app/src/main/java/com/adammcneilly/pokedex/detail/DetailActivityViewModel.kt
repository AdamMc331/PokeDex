package com.adammcneilly.pokedex.detail

import androidx.lifecycle.MutableLiveData
import com.adammcneilly.pokedex.BaseObservableViewModel
import com.adammcneilly.pokedex.DispatcherProvider
import com.adammcneilly.pokedex.R
import com.adammcneilly.pokedex.models.Type
import com.adammcneilly.pokedex.network.PokemonRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailActivityViewModel(
    private val repository: PokemonRepository,
    private val pokemonName: String,
    dispatcherProvider: DispatcherProvider = DispatcherProvider()
) : BaseObservableViewModel() {
    private val state = MutableLiveData<DetailActivityState>()

    private val currentState: DetailActivityState
        get() = state.value ?: DetailActivityState()

    val title: String
        get() = pokemonName.capitalize()

    val toolbarColorRes: Int
        get() = currentState.pokemon?.sortedTypes?.firstOrNull()?.getColorRes() ?: R.color.colorPrimary

    val toolbarTextColorRes: Int
        get() = currentState.pokemon?.sortedTypes?.firstOrNull()?.getComplementaryColorRes() ?: R.color.mds_white

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

    private var job: Job? = null

    init {
        job = CoroutineScope(dispatcherProvider.IO).launch {
            withContext(dispatcherProvider.Main) {
                startLoading()
            }

            @Suppress("TooGenericExceptionCaught")
            val newState = try {
                val pokemon = repository.getPokemonDetail(pokemonName)
                currentState.copy(loading = false, pokemon = pokemon, error = null)
            } catch (error: Throwable) {
                currentState.copy(loading = false, pokemon = null, error = error)
            }

            withContext(dispatcherProvider.Main) {
                setState(newState)
            }
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

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }
}