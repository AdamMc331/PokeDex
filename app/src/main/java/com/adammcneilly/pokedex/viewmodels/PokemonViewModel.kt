package com.adammcneilly.pokedex.viewmodels

import androidx.databinding.BaseObservable
import com.adammcneilly.pokedex.models.Pokemon
import com.adammcneilly.pokedex.models.Type

class PokemonViewModel : BaseObservable() {
    var pokemon: Pokemon? = null
        set(value) {
            field = value
            notifyChange()
        }

    val name: String
        get() = pokemon?.name?.capitalize().orEmpty()

    val types: List<Type>
        get() = pokemon?.types
            ?.sortedBy {
                it.slot
            }
            ?.mapNotNull {
                it.type
            }
            .orEmpty()
}