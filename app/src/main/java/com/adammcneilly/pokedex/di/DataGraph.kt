package com.adammcneilly.pokedex.di

interface DataGraph {
    val networkGraph: NetworkGraph
}

class LiveDataGraph(
    useGraphQL: Boolean
) : DataGraph {

    override val networkGraph: NetworkGraph = if (useGraphQL) {
        ApolloNetworkGraph()
    } else {
        RetrofitNetworkGraph()
    }
}