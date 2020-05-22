package com.adammcneilly.pokedex.redux

interface Middleware {
    fun dispatch(action: Action, next: NextDispatcher)
}