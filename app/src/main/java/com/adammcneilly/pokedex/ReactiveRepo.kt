package com.adammcneilly.pokedex

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.consumeAsFlow

interface DataSource {
    suspend fun getData(): Any
}

class ReactiveRepo(private val dataSource: DataSource) {
    private val actionChannel: Channel<Action> = Channel()

    val actionFlow: Flow<Action> = actionChannel.consumeAsFlow()

    suspend fun loadData() {
        actionChannel.send(Action.Loading)

        val data = dataSource.getData()
        actionChannel.send(Action.Loaded(data))
    }
}

sealed class Action {
    object Loading : Action()
    data class Loaded(val data: Any) : Action()
}