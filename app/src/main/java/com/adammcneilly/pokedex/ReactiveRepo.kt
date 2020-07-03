package com.adammcneilly.pokedex

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

interface DataSource {
    suspend fun getData(): Any
}

class ReactiveRepo(
    private val dataSource: DataSource,
    private val dataDispatcher: CoroutineDispatcher = Dispatchers.IO
) {
    private val actionChannel: Channel<Action> = Channel()

    val actionFlow: Flow<Action> = actionChannel.consumeAsFlow()

    suspend fun loadData() {
        actionChannel.send(Action.Loading)

        val data = dataSource.getData()

        actionChannel.send(Action.Loaded(data))
    }

    fun loadDataChannelFlow(): Flow<Action> {
        return channelFlow {
            send(Action.Loading)

            launch(Dispatchers.Unconfined) {
                val data = dataSource.getData()
                send(Action.Loaded(data))
            }
        }
    }

    fun loadDataFlow(): Flow<Action> {
        return flow {
            emit(Action.Loading)

            val data = dataSource.getData()
            emit(Action.Loaded(data))
        }
    }

    fun cleanUp() {
        actionChannel.close()
    }
}

sealed class Action {
    object Loading : Action()
    data class Loaded(val data: Any) : Action()
}