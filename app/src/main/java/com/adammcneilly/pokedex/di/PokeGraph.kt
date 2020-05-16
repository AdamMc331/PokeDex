package com.adammcneilly.pokedex.di

interface PokeGraph {
    val networkGraph: NetworkGraph
    val localStorageGraph: LocalStorageGraph
    val dataGraph: DataGraph
}