package com.adammcneilly.pokedex.pokemonlist

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.adammcneilly.pokedex.PokeApp
import com.adammcneilly.pokedex.databinding.FragmentPokemonListBinding
import com.adammcneilly.pokedex.detail.DetailActivity
import com.adammcneilly.pokedex.models.Pokemon
import com.adammcneilly.pokedex.network.PokemonAPI
import com.adammcneilly.pokedex.network.PokemonRetrofitService
import com.adammcneilly.pokedex.views.PokemonAdapter

class PokemonListFragment : Fragment() {

    private val pokemonAdapter = PokemonAdapter(this::pokemonClicked)

    private lateinit var viewModel: PokemonListViewModel
    private lateinit var binding: FragmentPokemonListBinding

    private val viewModelFactory = object : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            val pokemonAPI =
                PokemonAPI.defaultInstance((activity?.application as? PokeApp)?.baseUrl.orEmpty())
            val repository = PokemonRetrofitService(pokemonAPI)

            @Suppress("UNCHECKED_CAST")
            return PokemonListViewModel(repository) as T
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel =
            ViewModelProviders.of(this, viewModelFactory).get(PokemonListViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPokemonListBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this

        setupRecyclerView()
        setupViewModel()

        return binding.root
    }

    private fun setupRecyclerView() {
        val recyclerView = binding.pokemonList

        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = pokemonAdapter
        recyclerView.addItemDecoration(DividerItemDecoration(requireContext(), LinearLayoutManager.VERTICAL))
    }

    private fun setupViewModel() {
        binding.viewModel = viewModel

        viewModel.pokemon.observe(
            this,
            Observer {
                it?.let(pokemonAdapter::items::set)
            }
        )
    }

    private fun pokemonClicked(pokemon: Pokemon) {
        val intent = Intent(requireContext(), DetailActivity::class.java)
        intent.putExtra(DetailActivity.ARG_POKEMON_NAME, pokemon.name)
        startActivity(intent)
    }

    companion object {
        fun newInstance(): PokemonListFragment {
            return PokemonListFragment()
        }
    }
}