package com.adammcneilly.pokedex

import com.adammcneilly.pokedex.di.PokeGraph

class TestApplication : PokeApp() {
    override val pokeGraph: PokeGraph
        get() = AndroidTestPokeGraph(context = this)
}