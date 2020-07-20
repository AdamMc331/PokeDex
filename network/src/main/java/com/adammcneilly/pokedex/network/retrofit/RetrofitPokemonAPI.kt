package com.adammcneilly.pokedex.network.retrofit

import com.adammcneilly.pokedex.network.models.PokemonDTO
import com.adammcneilly.pokedex.network.models.PokemonResponseDTO
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.flow.Flow
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

internal interface RetrofitPokemonAPI {
    @GET("v2/pokemon")
    fun getPokemonAsync(): Deferred<PokemonResponseDTO>

    @GET("v2/pokemon/{name}")
    fun getPokemonDetailFlow(
        @Path("name")
        name: String
    ): Flow<PokemonDTO>

    companion object {
        fun defaultInstance(baseUrl: String): RetrofitPokemonAPI {
            val interceptor = HttpLoggingInterceptor().apply {
                this.level = HttpLoggingInterceptor.Level.BODY
            }

            val client = OkHttpClient.Builder().addInterceptor(interceptor).build()

            return Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(MoshiConverterFactory.create())
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .client(client)
                .build()
                .create(RetrofitPokemonAPI::class.java)
        }
    }
}