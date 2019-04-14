package com.adammcneilly.pokedex.main

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
import com.adammcneilly.pokedex.R
import com.adammcneilly.pokedex.databinding.ActivityMainBinding
import com.adammcneilly.pokedex.network.PokemonAPI
import com.adammcneilly.pokedex.network.PokemonRepository
import com.adammcneilly.pokedex.views.PokemonAdapter
import io.reactivex.disposables.CompositeDisposable

class MainActivity : AppCompatActivity() {
    private val pokemonAdapter = PokemonAdapter()
    private val compositeDisposable = CompositeDisposable()

    private lateinit var viewModel: MainActivityViewModel
    private lateinit var binding: ActivityMainBinding

    private val viewModelFactory = object : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            val pokemonAPI = PokemonAPI.defaultInstance
            val repository = PokemonRepository(pokemonAPI, compositeDisposable)

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

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.dispose()
    }

    private fun setupRecyclerView() {
        val recyclerView = findViewById<RecyclerView>(R.id.pokemon_list)

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = pokemonAdapter
        recyclerView.addItemDecoration(DividerItemDecoration(this, LinearLayoutManager.VERTICAL))
    }

    private fun setupViewModel() {
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(MainActivityViewModel::class.java)
        binding.viewModel = viewModel

        viewModel.pokemon.observe(this, Observer {
            it?.let(pokemonAdapter::items::set)
        })
    }
}
