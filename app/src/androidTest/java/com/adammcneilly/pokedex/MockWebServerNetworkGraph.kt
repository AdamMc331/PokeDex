package com.adammcneilly.pokedex

import com.adammcneilly.pokedex.di.NetworkGraph
import com.adammcneilly.pokedex.network.PokemonAPI
import com.adammcneilly.pokedex.network.retrofit.RetrofitService

class MockWebServerNetworkGraph : NetworkGraph {
    override val api: PokemonAPI
        get() = RetrofitService(MOCK_WEB_SERVER_URL)

    companion object {
        private val MOCK_WEB_SERVER_URL = "http://127.0.0.1:${BuildConfig.PORT}"
    }
}