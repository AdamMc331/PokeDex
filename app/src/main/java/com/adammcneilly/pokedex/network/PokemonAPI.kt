package com.adammcneilly.pokedex.network

import com.adammcneilly.pokedex.PokeApp
import com.adammcneilly.pokedex.models.PokemonResponse
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

interface PokemonAPI {
    @GET("v2/pokemon")
    fun getPokemon(): Single<PokemonResponse>

    companion object {
        val defaultInstance: PokemonAPI
            get() = Retrofit.Builder()
                .baseUrl(PokeApp.instance.baseUrl)
                .addConverterFactory(MoshiConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
                .create(PokemonAPI::class.java)
    }
}