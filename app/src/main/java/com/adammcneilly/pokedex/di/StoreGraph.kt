package com.adammcneilly.pokedex.di

import com.adammcneilly.pokedex.detail.PokemonDetailReducer
import com.adammcneilly.pokedex.detail.PokemonDetailState
import com.adammcneilly.pokedex.redux.Middleware
import com.adammcneilly.pokedex.redux.Store
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
interface StoreGraph {
    fun pokemonDetailStore(middlewares: List<Middleware>): Store<PokemonDetailState>
}

@ExperimentalCoroutinesApi
class ReduxStoreGraph : StoreGraph {

    override fun pokemonDetailStore(middlewares: List<Middleware>): Store<PokemonDetailState> {
        return Store(
            initialState = PokemonDetailState.Loading,
            middlewares = middlewares,
            reducer = PokemonDetailReducer()
        )
    }
}