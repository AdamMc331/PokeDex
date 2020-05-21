package com.adammcneilly.pokedex.di

import android.content.Context

/**
 * This interface defines all of the application's dependencies and sub graphs of dependencies.
 */
interface PokeGraph {
    val networkGraph: NetworkGraph
    val localStorageGraph: LocalStorageGraph
    val dataGraph: DataGraph
    val viewModelFactoryGraph: ViewModelFactoryGraph
}

/**
 * This is a concrete implementation of a [PokeGraph] that allows us to create all of our application
 * graph dependencies in one spot without bloating the application class.
 */
open class BasePokeGraph(
    private val useGraphQL: Boolean,
    private val context: Context
) : PokeGraph {
    override val networkGraph: NetworkGraph by lazy {
        LiveServerNetworkGraph(useGraphQL)
    }

    override val localStorageGraph: LocalStorageGraph by lazy {
        SQLiteDatabaseGraph(context)
    }

    override val dataGraph: DataGraph by lazy {
        BaseDataGraph(
            networkGraph = networkGraph,
            localStorageGraph = localStorageGraph
        )
    }

    override val viewModelFactoryGraph: ViewModelFactoryGraph by lazy {
        BaseViewModelFactoryGraph(
            dataGraph = dataGraph
        )
    }
}