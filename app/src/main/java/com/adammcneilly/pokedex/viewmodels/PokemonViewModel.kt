package com.adammcneilly.pokedex.viewmodels

import androidx.databinding.BaseObservable
import com.adammcneilly.pokedex.models.Pokemon

class PokemonViewModel : BaseObservable() {
    var pokemon: Pokemon? = null
        set(value) {
            field = value
            notifyChange()
        }

    val name: String
        get() = pokemon?.name?.capitalize().orEmpty()
}