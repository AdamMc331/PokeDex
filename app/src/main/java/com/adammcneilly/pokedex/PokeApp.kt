package com.adammcneilly.pokedex

import android.app.Application

open class PokeApp : Application() {
    open val baseUrl: String
        get() = "https://pokeapi.co/api/"

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    companion object {
        lateinit var instance: PokeApp
            private set
    }
}