package com.adammcneilly.pokedex.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.adammcneilly.pokedex.R
import com.adammcneilly.pokedex.databinding.ActivityMainBinding
import com.adammcneilly.pokedex.detail.PokemonDetailFragment
import com.adammcneilly.pokedex.listeners.PokemonClickedListener
import com.adammcneilly.pokedex.models.Pokemon
import com.adammcneilly.pokedex.pokemonlist.PokemonListFragment

class MainActivity : AppCompatActivity(), PokemonClickedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityMainBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.lifecycleOwner = this
        setSupportActionBar(binding.toolbar)

        showPokemonListFragment()
    }

    private fun showPokemonListFragment() {
        val listFragment = PokemonListFragment.newInstance()
        listFragment.pokemonClickListener = this

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.container, listFragment)
            .commit()
    }

    override fun pokemonClicked(pokemon: Pokemon) {
        val detailFragment = PokemonDetailFragment.newInstance(pokemon.name.orEmpty())

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.container, detailFragment)
            .addToBackStack("DETAIL_FRAGMENT_${pokemon.name}")
            .commit()
    }
}
