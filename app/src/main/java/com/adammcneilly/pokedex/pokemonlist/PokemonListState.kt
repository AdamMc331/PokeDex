package com.adammcneilly.pokedex.pokemonlist

import com.adammcneilly.pokedex.core.PokemonResponse

sealed class PokemonListState {
    object Loading : PokemonListState()
    class Loaded(val data: PokemonResponse) : PokemonListState()
    class Error(val error: Throwable?) : PokemonListState()
}