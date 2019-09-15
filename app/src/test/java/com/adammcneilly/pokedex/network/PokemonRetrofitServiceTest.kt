package com.adammcneilly.pokedex.network

import com.adammcneilly.pokedex.data.remote.PokemonAPI
import com.adammcneilly.pokedex.data.remote.PokemonRetrofitService
import com.adammcneilly.pokedex.models.Pokemon
import com.adammcneilly.pokedex.models.PokemonResponse
import com.adammcneilly.pokedex.whenever
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.fail
import org.junit.Test
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mockito.mock

@Suppress("UNCHECKED_CAST")
class PokemonRetrofitServiceTest {
    private val mockAPI = mock(PokemonAPI::class.java)
    private val retrofitService =
        PokemonRetrofitService(mockAPI)

    @Test
    fun getPokemon() {
        runBlocking {
            val testResult = PokemonResponse(count = 10)

            val mockDeferred = mock(Deferred::class.java) as Deferred<PokemonResponse>
            whenever(mockDeferred.await()).thenReturn(testResult)
            whenever(mockAPI.getPokemonAsync()).thenReturn(mockDeferred)

            val result = retrofitService.getPokemon()
            assertEquals(testResult, result)
        }
    }

    @Test
    fun getPokemonError() {
        runBlocking {
            val mockDeferred = mock(Deferred::class.java) as Deferred<PokemonResponse>
            whenever(mockDeferred.await()).thenThrow(java.lang.IllegalArgumentException())
            whenever(mockAPI.getPokemonAsync()).thenReturn(mockDeferred)

            try {
                retrofitService.getPokemon()
                fail("Expected network call to fail.")
            } catch (error: Throwable) {
            }
        }
    }

    @Test
    fun getPokemonDetail() {
        runBlocking {
            val testPokemon = Pokemon(name = "Adam")

            val mockDeferred = mock(Deferred::class.java) as Deferred<Pokemon>
            whenever(mockDeferred.await()).thenReturn(testPokemon)
            whenever(mockAPI.getPokemonDetailAsync(anyString())).thenReturn(mockDeferred)

            val result = retrofitService.getPokemonDetail("")
            assertEquals(testPokemon, result)
        }
    }

    @Test
    fun getPokemonDetailError() {
        runBlocking {
            val mockDeferred = mock(Deferred::class.java) as Deferred<Pokemon>
            whenever(mockDeferred.await()).thenThrow(IllegalArgumentException())
            whenever(mockAPI.getPokemonDetailAsync(anyString())).thenReturn(mockDeferred)

            try {
                retrofitService.getPokemonDetail("")
                fail("Expected network call to fail.")
            } catch (error: Throwable) {
            }
        }
    }
}