package com.adammcneilly.pokedex.models

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

@Entity
data class Pokemon(
    val name: String? = null,
    val url: String? = null,
    val types: List<TypeSlot>? = null,
    @Embedded val sprites: Sprites? = null,
    @PrimaryKey val _id: Long = 0
) {
    val sortedTypes: List<Type>
        get() = types?.sortedBy { it.slot }?.mapNotNull { it.type }.orEmpty()
}

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