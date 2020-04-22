package com.adammcneilly.pokedex.database.room

import androidx.room.TypeConverter
import com.adammcneilly.pokedex.core.Type

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