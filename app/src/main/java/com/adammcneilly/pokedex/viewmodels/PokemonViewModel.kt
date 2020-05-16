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

    val imageUrl: String?
        get() {
            val currentUrl = pokemon?.frontSpriteUrl.orEmpty()

            return if (currentUrl.isNotEmpty()) {
                currentUrl
            } else {
                pokemon?.pokedexNumber?.let { pokedexNumber ->
                    POKE_API_SPRITE_FORMAT.format(pokedexNumber)
                }
            }
        }

    companion object {
        /**
         * We use the open source PokeApi.co to power this app, but unfortunately their pokemon list
         * endpoint does not supply sprites with it. However, each sprite follows the same format,
         * so we can just use this string and format it with the corresponding pokedex number
         * if we don't have an imageUrl.
         */
        private const val POKE_API_SPRITE_FORMAT =
            "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/%s.png"
    }
}