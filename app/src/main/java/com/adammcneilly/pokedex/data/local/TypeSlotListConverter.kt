package com.adammcneilly.pokedex.data.local

import androidx.room.TypeConverter
import com.adammcneilly.pokedex.models.TypeSlot
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class TypeSlotListConverter {

    @TypeConverter
    fun fromTypeSlotList(value: List<TypeSlot>?): String {
        val gson = Gson()
        val type = object : TypeToken<List<TypeSlot>>() {}.type
        return gson.toJson(value, type)
    }

    @TypeConverter
    fun toTypeSlotList(value: String?): List<TypeSlot>? {
        val gson = Gson()
        val type = object : TypeToken<List<TypeSlot>>() {}.type
        return gson.fromJson(value, type)
    }
}
