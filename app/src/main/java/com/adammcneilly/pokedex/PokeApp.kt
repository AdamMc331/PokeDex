package com.adammcneilly.pokedex

import android.app.Application
import com.adammcneilly.pokedex.database.PokedexDatabase
import com.adammcneilly.pokedex.database.RoomDatabase
import com.adammcneilly.pokedex.network.PokemonAPI
import com.adammcneilly.pokedex.network.apollo.ApolloService
import com.adammcneilly.pokedex.network.retrofit.RetrofitService

open class PokeApp : Application() {
    /**
     * TODO: Let's make this user adjustable, maybe some settings fragment?
     */
    private val useGraphQL: Boolean = true

    open val baseUrl: String
        get() = if (useGraphQL) {
            GRAPHQL_API_URL
        } else {
            REST_API_URL
        }

    val networkService: PokemonAPI by lazy {
        if (useGraphQL) {
            ApolloService(baseUrl)
        } else {
            RetrofitService(baseUrl)
        }
    }

    val databaseService: PokedexDatabase by lazy {
        RoomDatabase(this)
    }

    companion object {
        private const val REST_API_URL = "https://pokeapi.co/api/"
        private const val GRAPHQL_API_URL = "https://graphql-pokemon.now.sh/"
    }
}