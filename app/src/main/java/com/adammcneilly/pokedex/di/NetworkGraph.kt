package com.adammcneilly.pokedex.di

import com.adammcneilly.pokedex.network.PokemonAPI
import com.adammcneilly.pokedex.network.apollo.ApolloService
import com.adammcneilly.pokedex.network.retrofit.RetrofitService

/**
 * This dependency injection graph maintains all of our dependencies for communicate with a remote
 * server.
 */
interface NetworkGraph {
    val api: PokemonAPI
}

/**
 * This implementation of a [NetworkGraph] communicates with a live server, as opposed to one
 * that could be used for mocking data.
 */
class LiveServerNetworkGraph(
    useGraphQL: Boolean
) : NetworkGraph {
    override val api: PokemonAPI = if (useGraphQL) {
        ApolloService(GRAPHQL_BASE_URL)
    } else {
        RetrofitService(REST_API_URL)
    }

    companion object {
        private const val REST_API_URL = "https://pokeapi.co/api/"
        private const val GRAPHQL_BASE_URL = "https://graphql-pokemon.now.sh/"
    }
}