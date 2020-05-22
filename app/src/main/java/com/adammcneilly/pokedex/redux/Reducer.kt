package com.adammcneilly.pokedex.redux

interface Reducer<S : State> {
    fun reduce(state: S, action: Action): S
}