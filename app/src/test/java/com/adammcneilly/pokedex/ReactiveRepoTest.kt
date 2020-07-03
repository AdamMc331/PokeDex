package com.adammcneilly.pokedex

import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test

class ReactiveRepoTest {

    @Test
    fun loadData() = runBlocking {
        val dataSource = FakeDataSource()
        val repo = ReactiveRepo(dataSource)

        launch {
            repo.loadData()
            repo.cleanUp()
        }

        val events = repo.loadDataChannelFlow().toList(mutableListOf())

        assertEquals(2, events.size)
    }
}

private class FakeDataSource : DataSource {
    override suspend fun getData(): Any {
        return "Testing"
    }
}