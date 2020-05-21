@file:Suppress("UNCHECKED_CAST")

package com.adammcneilly.pokedex.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.adammcneilly.pokedex.detail.PokemonDetailViewModel
import com.adammcneilly.pokedex.pokemonlist.PokemonListViewModel

/**
 * This graph exposes all of the necessary [ViewModelProvider.Factory] implementations
 * for this application.
 */
interface ViewModelFactoryGraph {
    fun pokemonListViewModelFactory(): ViewModelProvider.Factory

    fun pokemonDetailViewModelFactory(pokemonName: String): ViewModelProvider.Factory
}

class BaseViewModelFactoryGraph(
    private val dataGraph: DataGraph
) : ViewModelFactoryGraph {
    override fun pokemonListViewModelFactory(): ViewModelProvider.Factory {
        return PokemonListViewModelFactory(
            dataGraph = dataGraph
        )
    }

    override fun pokemonDetailViewModelFactory(pokemonName: String): ViewModelProvider.Factory {
        return PokemonDetailViewModelFactory(
            dataGraph = dataGraph,
            pokemonName = pokemonName
        )
    }
}

class PokemonListViewModelFactory(
    private val dataGraph: DataGraph
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return PokemonListViewModel(
            repository = dataGraph.pokemonRepository
        ) as T
    }
}

class PokemonDetailViewModelFactory(
    private val dataGraph: DataGraph,
    private val pokemonName: String
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return PokemonDetailViewModel(
            repository = dataGraph.pokemonRepository,
            pokemonName = pokemonName
        ) as T
    }
}