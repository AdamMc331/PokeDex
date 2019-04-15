package com.adammcneilly.pokedex.network

import com.adammcneilly.pokedex.PokeApp
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
        val defaultInstance: PokemonAPI
            get() = Retrofit.Builder()
                .baseUrl(PokeApp.instance.baseUrl)
                .addConverterFactory(MoshiConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(OkHttpClient.Builder().addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)).build())
                .build()
                .create(PokemonAPI::class.java)
    }
}