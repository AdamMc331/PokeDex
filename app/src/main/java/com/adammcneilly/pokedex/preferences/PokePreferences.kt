package com.adammcneilly.pokedex.preferences

import android.content.SharedPreferences

interface PokePreferences {
    fun getBoolean(key: String, default: Boolean): Boolean
}

class AndroidPreferences(private val preferences: SharedPreferences) : PokePreferences {
    override fun getBoolean(key: String, default: Boolean): Boolean {
        return preferences.getBoolean(key, default)
    }
}