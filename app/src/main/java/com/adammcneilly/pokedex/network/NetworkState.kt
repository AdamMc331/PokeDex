package com.adammcneilly.pokedex.network

sealed class NetworkState {
    object Loading : NetworkState()
    class Loaded<T>(val data: T) : NetworkState()
    class Error(val error: Throwable?) : NetworkState()
}