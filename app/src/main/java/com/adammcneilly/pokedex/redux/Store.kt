package com.adammcneilly.pokedex.redux

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

@ExperimentalCoroutinesApi
class Store<S : State>(
    initialState: S,
    middlewares: List<Middleware> = emptyList(),
    private val reducer: Reducer<S>
) {
    private val _state = MutableStateFlow(initialState)

    val state: Flow<S> = _state

    private var dispatchers: List<NextDispatcher> = emptyList()

    init {
        populateDispatchers(middlewares)
    }

    private fun populateDispatchers(middlewares: List<Middleware>) {
        val dispatchers = mutableListOf<NextDispatcher>()

        // We will always have at least one dispatcher, which is one that proxies the action to our reducer.
        val reducerDispatcher = object : NextDispatcher {
            override fun dispatch(action: Action) {
                _state.value = reducer.reduce(_state.value, action)
            }
        }
        dispatchers.add(reducerDispatcher)

        // Loop through our middleware backwards, and have each one point to whatever is currently
        // first in the list. This creates a LinkedList more or less.
        middlewares.reversed().map { middleware ->
            val nextDispatcher = dispatchers[0]

            val newDispatcher = object : NextDispatcher {
                override fun dispatch(action: Action) {
                    middleware.dispatch(action, nextDispatcher)
                }
            }

            dispatchers.add(0, newDispatcher)
        }

        this.dispatchers = dispatchers
    }

    fun dispatch(action: Action) {
        dispatchers.first().dispatch(action)
    }
}