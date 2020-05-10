package com.adammcneilly.pokedex.database.room

import androidx.room.TypeConverter
import com.adammcneilly.pokedex.core.Type

/**
 * TODO: This needs a test but also this is probably not a good idea.
 */
internal class PokemonTypeConverter {

    @TypeConverter
    fun fromInt(ordinal: Int?): Type? {
        return Type.values().find { type ->
            type.ordinal == ordinal
        }
    }

    @TypeConverter
    fun toInt(type: Type?): Int? {
        return type?.ordinal
    }
}