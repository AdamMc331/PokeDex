package com.adammcneilly.pokedex

import android.app.Application
import android.content.Context
import androidx.preference.PreferenceManager
import com.adammcneilly.pokedex.di.DataGraph
import com.adammcneilly.pokedex.di.LiveDataGraph
import com.adammcneilly.pokedex.di.PokeGraph
import com.adammcneilly.pokedex.preferences.AndroidPreferences
import com.adammcneilly.pokedex.preferences.PokePreferences

open class PokeApp : Application(), PokeGraph {

    override val dataGraph: DataGraph
        get() {
            val useGraphQL = this.getPreferences().getBoolean("useGraphQL", false)

            return LiveDataGraph(useGraphQL)
        }

    open val restBaseUrl: String
        get() = "https://pokeapi.co/api/"

    private fun getPreferences(): PokePreferences {
        val defaultPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        return AndroidPreferences(defaultPreferences)
    }
}

fun Context.pokeGraph(): PokeGraph {
    return (this.applicationContext as PokeGraph)
}