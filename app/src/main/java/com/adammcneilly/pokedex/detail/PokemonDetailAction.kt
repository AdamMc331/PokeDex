package com.adammcneilly.pokedex.detail

import com.adammcneilly.pokedex.core.Pokemon
import com.adammcneilly.pokedex.redux.Action
import kotlinx.coroutines.CoroutineScope

sealed class PokemonDetailAction : Action {
    data class FetchPokemon(
        val pokemonName: String,
        val requestScope: CoroutineScope
    ) : PokemonDetailAction()

    object Loading : PokemonDetailAction()
    data class Loaded(val detail: Pokemon) : PokemonDetailAction()
    data class Error(val errorMessage: String) : PokemonDetailAction()
}