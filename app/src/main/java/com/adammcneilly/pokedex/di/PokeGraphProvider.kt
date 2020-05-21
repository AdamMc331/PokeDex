package com.adammcneilly.pokedex.di

/**
 * This is a wrapper around a [PokeGraph] that allows us to create a PokeGraph by itself, and that
 * way our application can just implement this interface, and not have to create all the dependencies.
 */
interface PokeGraphProvider {
    val pokeGraph: PokeGraph
}