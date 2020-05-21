package com.adammcneilly.pokedex

import android.content.Context
import com.adammcneilly.pokedex.di.BasePokeGraph
import com.adammcneilly.pokedex.di.NetworkGraph

/**
 * An extension on [BasePokeGraph] that provides dependencies for our AndroidTest cases.
 */
class AndroidTestPokeGraph(context: Context) : BasePokeGraph(
    useGraphQL = false,
    context = context
) {
    override val networkGraph: NetworkGraph
        get() = MockWebServerNetworkGraph()
}