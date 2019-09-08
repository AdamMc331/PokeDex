package com.adammcneilly.pokedex.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.adammcneilly.pokedex.R
import com.adammcneilly.pokedex.databinding.ActivityMainBinding
import com.adammcneilly.pokedex.pokemonlist.PokemonListFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityMainBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.lifecycleOwner = this
        setSupportActionBar(binding.toolbar)

        showPokemonListFragment()
    }

    private fun showPokemonListFragment() {
        val fragment = PokemonListFragment.newInstance()

        supportFragmentManager.beginTransaction()
            .replace(R.id.container, fragment)
            .commit()
    }
}
