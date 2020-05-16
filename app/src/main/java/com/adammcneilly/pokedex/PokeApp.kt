package com.adammcneilly.pokedex

import android.app.Application
import android.content.Context
import androidx.preference.PreferenceManager
import com.adammcneilly.pokedex.di.BaseDataGraph
import com.adammcneilly.pokedex.di.DataGraph
import com.adammcneilly.pokedex.di.LiveServerNetworkGraph
import com.adammcneilly.pokedex.di.LocalStorageGraph
import com.adammcneilly.pokedex.di.NetworkGraph
import com.adammcneilly.pokedex.di.PokeGraph
import com.adammcneilly.pokedex.di.SQLiteDatabaseGraph
import com.adammcneilly.pokedex.preferences.AndroidPreferences
import com.adammcneilly.pokedex.preferences.PokePreferences

open class PokeApp : Application(), PokeGraph {

    override val networkGraph: NetworkGraph by lazy {
        val useGraphQL = this.getPreferences().getBoolean("useGraphQL", false)

        LiveServerNetworkGraph(useGraphQL)
    }

    override val localStorageGraph: LocalStorageGraph by lazy {
        SQLiteDatabaseGraph(this)
    }

    override val dataGraph: DataGraph by lazy {
        BaseDataGraph(
            networkGraph = networkGraph,
            localStorageGraph = localStorageGraph
        )
    }

    private fun getPreferences(): PokePreferences {
        val defaultPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        return AndroidPreferences(defaultPreferences)
    }
}

fun Context.pokeGraph(): PokeGraph {
    return (this.applicationContext as PokeGraph)
}