package com.adammcneilly.pokedex.database.room

import androidx.room.TypeConverter
import com.adammcneilly.pokedex.database.models.PersistableTypeSlot
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

internal class TypeSlotListConverter {

    @TypeConverter
    fun fromTypeSlotList(value: List<PersistableTypeSlot>?): String {
        val gson = Gson()
        val type = object : TypeToken<List<PersistableTypeSlot>>() {}.type
        return gson.toJson(value, type)
    }

    @TypeConverter
    fun toTypeSlotList(value: String?): List<PersistableTypeSlot>? {
        val gson = Gson()
        val type = object : TypeToken<List<PersistableTypeSlot>>() {}.type
        return gson.fromJson(value, type)
    }
}