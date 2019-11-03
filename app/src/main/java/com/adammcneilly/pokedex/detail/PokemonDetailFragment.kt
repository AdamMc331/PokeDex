package com.adammcneilly.pokedex.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.navArgs
import com.adammcneilly.pokedex.PokeApp
import com.adammcneilly.pokedex.data.PokemonService
import com.adammcneilly.pokedex.database.DefaultPokedexDatabase
import com.adammcneilly.pokedex.databinding.FragmentPokemonDetailBinding
import com.adammcneilly.pokedex.network.DefaultPokemonAPI

class PokemonDetailFragment : Fragment() {
    private lateinit var binding: FragmentPokemonDetailBinding
    private lateinit var viewModel: PokemonDetailViewModel

    private val viewModelFactory = object : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            val api = DefaultPokemonAPI((activity?.application as? PokeApp)?.baseUrl.orEmpty())
            val database = DefaultPokedexDatabase(requireContext())
            val repository = PokemonService(database, api)
            val arguments: PokemonDetailFragmentArgs by navArgs()
            val pokemonName = arguments.pokemonName

            @Suppress("UNCHECKED_CAST")
            return PokemonDetailViewModel(repository, pokemonName) as T
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel =
            ViewModelProviders.of(this, viewModelFactory).get(PokemonDetailViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPokemonDetailBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        setupViewModel()
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        (activity as? AppCompatActivity)?.supportActionBar?.title = viewModel.title
    }

    private fun setupViewModel() {
        binding.viewModel = viewModel
    }
}