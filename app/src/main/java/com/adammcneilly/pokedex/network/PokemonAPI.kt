package com.adammcneilly.pokedex.network

import com.adammcneilly.pokedex.models.Pokemon
import com.adammcneilly.pokedex.models.PokemonResponse
import com.adammcneilly.pokedex.models.Species
import io.reactivex.Single
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

interface PokemonAPI {
    @GET("v2/pokemon")
    fun getPokemon(): Single<PokemonResponse>

    @GET("v2/pokemon/{name}")
    fun getPokemonByName(@Path("name") name: String): Single<Pokemon>

    @GET("v2/pokemon-species/{name}")
    fun getPokemonSpecies(@Path("name") name: String): Single<Species>

    companion object {
        fun defaultInstance(baseUrl: String): PokemonAPI {
            val interceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
            val client = OkHttpClient.Builder().addInterceptor(interceptor).build()
            return Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(MoshiConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(client)
                .build()
                .create(PokemonAPI::class.java)
        }
    }
}