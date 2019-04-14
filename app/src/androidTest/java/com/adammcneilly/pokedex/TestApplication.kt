package com.adammcneilly.pokedex

class TestApplication : PokeApp() {
    override fun onCreate() {
        super.onCreate()
        sInstance = this
    }

    override val baseUrl: String
        get() = "http://127.0.0.1:8080"

    companion object {
        private var sInstance: TestApplication? = null

        @Synchronized
        fun getInstance(): TestApplication {
            return sInstance!!
        }
    }
}