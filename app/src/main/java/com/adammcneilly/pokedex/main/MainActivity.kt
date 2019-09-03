package com.adammcneilly.pokedex.main

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.adammcneilly.pokedex.PokeApp
import com.adammcneilly.pokedex.R
import com.adammcneilly.pokedex.databinding.ActivityMainBinding
import com.adammcneilly.pokedex.detail.DetailActivity
import com.adammcneilly.pokedex.models.Pokemon
import com.adammcneilly.pokedex.network.PokemonAPI
import com.adammcneilly.pokedex.network.PokemonRetrofitService
import com.adammcneilly.pokedex.views.PokemonAdapter

class MainActivity : AppCompatActivity() {
    private val pokemonAdapter = PokemonAdapter(this::pokemonClicked)

    private lateinit var viewModel: MainActivityViewModel
    private lateinit var binding: ActivityMainBinding

    private val viewModelFactory = object : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            val pokemonAPI =
                PokemonAPI.defaultInstance((application as? PokeApp)?.baseUrl.orEmpty())
            val repository = PokemonRetrofitService(pokemonAPI)

            @Suppress("UNCHECKED_CAST")
            return MainActivityViewModel(repository) as T
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        setSupportActionBar(binding.toolbar)

        setupRecyclerView()
        setupViewModel()
    }

    private fun setupRecyclerView() {
        val recyclerView = findViewById<RecyclerView>(R.id.pokemon_list)

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = pokemonAdapter
        recyclerView.addItemDecoration(DividerItemDecoration(this, LinearLayoutManager.VERTICAL))
    }

    private fun setupViewModel() {
        viewModel =
            ViewModelProviders.of(this, viewModelFactory).get(MainActivityViewModel::class.java)
        binding.viewModel = viewModel

        viewModel.pokemon.observe(
            this,
            Observer {
                it?.let(pokemonAdapter::items::set)
            }
        )
    }

    private fun pokemonClicked(pokemon: Pokemon) {
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra(DetailActivity.ARG_POKEMON_NAME, pokemon.name)
        startActivity(intent)
    }
}
