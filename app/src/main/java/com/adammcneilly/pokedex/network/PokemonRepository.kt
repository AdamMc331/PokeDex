package com.adammcneilly.pokedex.network

import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject

open class PokemonRepository(
    private val api: PokemonAPI,
    private val disposables: CompositeDisposable,
    private val processScheduler: Scheduler = Schedulers.io(),
    private val observerScheduler: Scheduler = AndroidSchedulers.mainThread()
) {
    val pokemonResponseState = PublishSubject.create<NetworkState>()
    val pokemonState = PublishSubject.create<NetworkState>()
    val pokemonSpecies = PublishSubject.create<NetworkState>()

    fun fetchPokemon() {
        val subscription = api.getPokemon()
            .subscribeOn(processScheduler)
            .observeOn(observerScheduler)
            .map {
                NetworkState.Loaded(it) as NetworkState
            }
            .doOnSubscribe {
                pokemonResponseState.onNext(NetworkState.Loading)
            }
            .onErrorReturn {
                NetworkState.Error(it)
            }
            .subscribe(pokemonResponseState::onNext)

        disposables.add(subscription)
    }

    fun fetchPokemonByName(name: String) {
        val subscription = api.getPokemonByName(name)
            .subscribeOn(processScheduler)
            .observeOn(observerScheduler)
            .map {
                NetworkState.Loaded(it) as NetworkState
            }
            .doOnSubscribe {
                pokemonState.onNext(NetworkState.Loading)
            }
            .onErrorReturn {
                NetworkState.Error(it)
            }
            .subscribe(pokemonState::onNext)

        disposables.add(subscription)
    }

    fun fetchPokemonSpecies(name: String) {
        val subscription = api.getPokemonSpecies(name)
            .subscribeOn(processScheduler)
            .observeOn(observerScheduler)
            .map {
                NetworkState.Loaded(it) as NetworkState
            }
            .doOnSubscribe {
                pokemonSpecies.onNext(NetworkState.Loading)
            }
            .onErrorReturn {
                NetworkState.Error(it)
            }
            .subscribe(pokemonSpecies::onNext)

        disposables.add(subscription)
    }
}