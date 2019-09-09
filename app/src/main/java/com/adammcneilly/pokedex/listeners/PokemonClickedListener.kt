package com.adammcneilly.pokedex.listeners

import com.adammcneilly.pokedex.models.Pokemon

interface PokemonClickedListener {
    fun pokemonClicked(pokemon: Pokemon)
}