package com.adammcneilly.pokedex

import kotlinx.coroutines.Dispatchers

class TestApplication : PokeApp() {
    override val baseUrl: String
        get() = "http://127.0.0.1:${BuildConfig.PORT}"

    override val dispatcherProvider: DispatcherProvider
        get() = DispatcherProvider(IO = Dispatchers.Unconfined)
}