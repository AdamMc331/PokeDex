package com.adammcneilly.pokedex.detail

import com.adammcneilly.pokedex.data.PokemonRepository
import com.adammcneilly.pokedex.redux.Action
import com.adammcneilly.pokedex.redux.Middleware
import com.adammcneilly.pokedex.redux.NextDispatcher
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

/**
 * This middleware is responsible for communicating with our [PokemonRepository].
 */
class PokemonDetailNetworkingMiddleware(
    private val repository: PokemonRepository
) : Middleware {
    override fun dispatch(action: Action, next: NextDispatcher) {
        when (action) {
            is PokemonDetailAction.FetchPokemon -> {
                fetchPokemon(action, next)
            }
            else -> next.dispatch(action)
        }
    }

    private fun fetchPokemon(
        action: PokemonDetailAction.FetchPokemon,
        next: NextDispatcher
    ) {
        action.requestScope.launch {
            repository
                .getPokemonDetail(pokemonName = action.pokemonName)
                .collect { action ->
                    next.dispatch(action)
                }
        }
    }
}