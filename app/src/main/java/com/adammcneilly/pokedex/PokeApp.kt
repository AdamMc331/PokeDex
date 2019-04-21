package com.adammcneilly.pokedex

import android.app.Application

open class PokeApp : Application() {
    open val baseUrl: String
        get() = "https://pokeapi.co/api/"

    /**
     * We'll have our app return the default dispatchers to use for coroutines, but make it open so that our UI tests
     * can override it.
     */
    open val dispatcherProvider: DispatcherProvider
        get() = DispatcherProvider()
}