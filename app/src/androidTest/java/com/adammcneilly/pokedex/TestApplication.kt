package com.adammcneilly.pokedex

class TestApplication : PokeApp() {
    override val baseUrl: String
        get() = "http://127.0.0.1:${BuildConfig.PORT}"
}
