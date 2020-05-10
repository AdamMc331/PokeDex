package com.adammcneilly.pokedex.database.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.adammcneilly.pokedex.core.Pokemon
import com.adammcneilly.pokedex.core.Type

/**
 * TODO: These need tests.
 */
@Entity
internal data class PersistablePokemon(
    @PrimaryKey val name: String = "",
    val frontSpriteUrl: String? = null,
    val firstType: Type = Type.UNKNOWN,
    val secondType: Type? = null,
    val pokedexNumber: String = ""
) {

    fun toPokemon(): Pokemon {
        return Pokemon(
            name = this.name,
            frontSpriteUrl = this.frontSpriteUrl,
            firstType = this.firstType,
            secondType = this.secondType,
            pokedexNumber = this.pokedexNumber
        )
    }

    companion object {
        fun fromPokemon(pokemon: Pokemon): PersistablePokemon {
            return PersistablePokemon(
                name = pokemon.name,
                frontSpriteUrl = pokemon.frontSpriteUrl,
                firstType = pokemon.firstType,
                secondType = pokemon.secondType,
                pokedexNumber = pokemon.pokedexNumber
            )
        }
    }
}