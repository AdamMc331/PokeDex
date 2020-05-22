package com.adammcneilly.pokedex.redux

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

@ExperimentalCoroutinesApi
class Store<S : State, A : Action>(
    initialState: S,
    private val reducer: Reducer<S, A>
) {
    private val _state = MutableStateFlow(initialState)

    val state: Flow<S> = _state

    fun dispatch(action: A) {
        _state.value = reducer.reduce(_state.value, action)
    }
}