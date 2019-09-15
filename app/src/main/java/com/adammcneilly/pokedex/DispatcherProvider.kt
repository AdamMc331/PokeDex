package com.adammcneilly.pokedex

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

@Suppress("ConstructorParameterNaming")
data class DispatcherProvider(
    val IO: CoroutineDispatcher = Dispatchers.IO,
    val Main: CoroutineDispatcher = Dispatchers.Main,
    val Unconfined: CoroutineDispatcher = Dispatchers.Unconfined
)
