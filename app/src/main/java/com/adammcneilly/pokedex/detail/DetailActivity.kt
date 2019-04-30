package com.adammcneilly.pokedex.detail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.adammcneilly.pokedex.PokeApp
import com.adammcneilly.pokedex.R
import com.adammcneilly.pokedex.databinding.ActivityDetailBinding
import com.adammcneilly.pokedex.network.PokemonAPI
import com.adammcneilly.pokedex.network.PokemonRepository

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private lateinit var viewModel: DetailActivityViewModel

    private val viewModelFactory = object : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            val pokemonAPI = PokemonAPI.defaultInstance((application as? PokeApp)?.baseUrl.orEmpty())
            val repository = PokemonRepository(pokemonAPI)
            val pokemonName = this@DetailActivity.intent.getStringExtra(ARG_POKEMON_NAME)

            @Suppress("UNCHECKED_CAST")
            return DetailActivityViewModel(repository, pokemonName) as T
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail)
        setSupportActionBar(binding.toolbar)

        setupViewModel()
    }

    private fun setupViewModel() {
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(DetailActivityViewModel::class.java)
        binding.viewModel = viewModel
    }

    companion object {
        const val ARG_POKEMON_NAME = "pokemonName"
    }
}
