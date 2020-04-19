package com.adammcneilly.pokedex

import android.app.Application
import com.adammcneilly.pokedex.network.PokemonAPI
import com.adammcneilly.pokedex.network.apollo.ApolloService
import com.adammcneilly.pokedex.network.retrofit.RetrofitService

open class PokeApp : Application() {
    open val restBaseUrl: String
        get() = "https://pokeapi.co/api/"

    private val graphQLBaseUrl: String = "https://graphql-pokemon.now.sh/"

    /**
     * Determines if the app's network requests should be served through GraphQL or not.
     * In the future, this should be part of a settings fragment.
     */
    private val useGraphQL: Boolean = true

    fun getPokemonAPI(): PokemonAPI {
        return if (useGraphQL) {
            ApolloService(graphQLBaseUrl)
        } else {
            RetrofitService(restBaseUrl)
        }
    }
}