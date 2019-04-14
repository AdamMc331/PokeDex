package com.adammcneilly.pokedex

class TestApplication : PokeApp() {
    override fun onCreate() {
        super.onCreate()
        sInstance = this
    }

    override val baseUrl: String
        get() = "http://127.0.0.1:${BuildConfig.PORT}"

    companion object {
        private var sInstance: TestApplication? = null

        @Synchronized
        fun getInstance(): TestApplication {
            return sInstance!!
        }
    }
}