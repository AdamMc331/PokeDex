package com.adammcneilly.pokedex.views

sealed class ViewState<T> {
    class Loading<T> : ViewState<T>()
    class Loaded<T>(val data: T) : ViewState<T>()
    class Error<T>(val error: Throwable?) : ViewState<T>()
}