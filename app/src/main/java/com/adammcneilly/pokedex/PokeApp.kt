package com.adammcneilly.pokedex

import android.app.Application
import androidx.preference.PreferenceManager
import com.adammcneilly.pokedex.network.PokemonAPI
import com.adammcneilly.pokedex.network.apollo.ApolloService
import com.adammcneilly.pokedex.network.retrofit.RetrofitService
import com.adammcneilly.pokedex.preferences.AndroidPreferences
import com.adammcneilly.pokedex.preferences.PokePreferences

open class PokeApp : Application() {
    open val restBaseUrl: String
        get() = "https://pokeapi.co/api/"

    private val graphQLBaseUrl: String = "https://graphql-pokemon.now.sh/"

    private fun getPreferences(): PokePreferences {
        val defaultPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        return AndroidPreferences(defaultPreferences)
    }

    fun getPokemonAPI(): PokemonAPI {
        val useGraphQL = this.getPreferences().getBoolean("useGraphQL", false)

        return if (useGraphQL) {
            ApolloService(graphQLBaseUrl)
        } else {
            RetrofitService(restBaseUrl)
        }
    }
}