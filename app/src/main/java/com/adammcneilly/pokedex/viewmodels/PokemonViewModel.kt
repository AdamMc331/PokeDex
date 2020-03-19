package com.adammcneilly.pokedex.viewmodels

import android.annotation.SuppressLint
import androidx.databinding.BaseObservable
import com.adammcneilly.pokedex.core.Pokemon

class PokemonViewModel : BaseObservable() {
    var pokemon: Pokemon? = null
        set(value) {
            field = value
            notifyChange()
        }

    val name: String
        @SuppressLint("DefaultLocale")
        get() = pokemon?.name?.capitalize().orEmpty()
}