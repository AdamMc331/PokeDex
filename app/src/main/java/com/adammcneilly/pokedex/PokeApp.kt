package com.adammcneilly.pokedex

import android.app.Application
import android.content.Context
import androidx.preference.PreferenceManager
import com.adammcneilly.pokedex.di.BasePokeGraph
import com.adammcneilly.pokedex.di.PokeGraph
import com.adammcneilly.pokedex.di.PokeGraphProvider
import com.adammcneilly.pokedex.preferences.AndroidPreferences
import com.adammcneilly.pokedex.preferences.PokePreferences

open class PokeApp : Application(), PokeGraphProvider {

    override val pokeGraph: PokeGraph by lazy {
        val useGraphQL = this.getPreferences().getBoolean("useGraphQL", false)

        BasePokeGraph(
            useGraphQL = useGraphQL,
            context = this
        )
    }

    private fun getPreferences(): PokePreferences {
        val defaultPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        return AndroidPreferences(defaultPreferences)
    }
}

fun Context.pokeGraph(): PokeGraph {
    return (this.applicationContext as PokeGraphProvider).pokeGraph
}