package com.adammcneilly.pokedex

class TestApplication : PokeApp() {
    override fun onCreate() {
        super.onCreate()
        sInstance = this
    }

    override val baseUrl: String
        get() = "http://127.0.0.1:${BuildConfig.PORT}"

    companion object {
        lateinit var sInstance: TestApplication
            private set
    }
}