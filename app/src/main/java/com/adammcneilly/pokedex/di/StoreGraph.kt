package com.adammcneilly.pokedex.di

import com.adammcneilly.pokedex.detail.PokemonDetailAction
import com.adammcneilly.pokedex.detail.PokemonDetailReducer
import com.adammcneilly.pokedex.detail.PokemonDetailState
import com.adammcneilly.pokedex.redux.Store
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
interface StoreGraph {
    val pokemonDetailStore: Store<PokemonDetailState, PokemonDetailAction>
}

@ExperimentalCoroutinesApi
class ReduxStoreGraph : StoreGraph {
    override val pokemonDetailStore: Store<PokemonDetailState, PokemonDetailAction> by lazy {
        Store(PokemonDetailState.Loading, PokemonDetailReducer())
    }
}