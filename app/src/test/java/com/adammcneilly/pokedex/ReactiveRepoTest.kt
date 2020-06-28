package com.adammcneilly.pokedex

import kotlinx.coroutines.flow.collect
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
        }

        repo.actionFlow.collect { action ->
            val expectedAction = Action.Loaded("Testing")
            assertEquals(expectedAction, action)
        }
    }
}

private class FakeDataSource : DataSource {
    override suspend fun getData(): Any {
        return "Testing"
    }
}