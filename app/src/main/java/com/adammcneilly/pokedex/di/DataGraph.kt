package com.adammcneilly.pokedex.di

import com.adammcneilly.pokedex.data.PokemonRepository
import com.adammcneilly.pokedex.data.PokemonService

/**
 * This dependency injection graph should contain all of the dependencies related to requesting data
 * from either local storage or a remote server.
 */
interface DataGraph {
    val pokemonRepository: PokemonRepository
}

/**
 * This implementation will create an instance of a [DataGraph] using the supplied information for
 * a network and local storage setup.
 */
class BaseDataGraph(
    networkGraph: NetworkGraph,
    localStorageGraph: LocalStorageGraph
) : DataGraph {
    override val pokemonRepository: PokemonRepository = PokemonService(
        database = localStorageGraph.database,
        api = networkGraph.api
    )
}