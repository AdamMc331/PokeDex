package com.adammcneilly.pokedex.di

import com.adammcneilly.pokedex.network.PokemonAPI
import com.adammcneilly.pokedex.network.retrofit.RetrofitService

interface NetworkGraph {
    val api: PokemonAPI
}

class RetrofitNetworkGraph : NetworkGraph {
    override val api: PokemonAPI
        get() = RetrofitService(REST_API_URL)

    companion object {
        private const val REST_API_URL = "https://pokeapi.co/api/"
    }
}

class ApolloNetworkGraph : NetworkGraph {
    override val api: PokemonAPI
        get() = TODO("Not yet implemented")
}