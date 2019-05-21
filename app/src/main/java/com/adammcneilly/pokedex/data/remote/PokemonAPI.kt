package com.adammcneilly.pokedex.data.remote

import com.adammcneilly.pokedex.models.Pokemon
import com.adammcneilly.pokedex.models.PokemonResponse
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.coroutines.Deferred
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

interface PokemonAPI {
    @GET("v2/pokemon")
    fun getPokemonAsync(): Deferred<PokemonResponse>

    @GET("v2/pokemon/{name}")
    fun getPokemonDetailAsync(@Path("name") name: String): Deferred<Pokemon>

    companion object {
        fun defaultInstance(baseUrl: String): PokemonAPI {
            val interceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
            val client = OkHttpClient.Builder().addInterceptor(interceptor).build()
            return Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(MoshiConverterFactory.create())
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .client(client)
                .build()
                .create(PokemonAPI::class.java)
        }
    }
}