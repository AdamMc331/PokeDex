package com.adammcneilly.pokedex.preferences

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import com.adammcneilly.pokedex.R

class PreferenceFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preferences, rootKey)
    }
}