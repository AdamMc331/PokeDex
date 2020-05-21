package com.adammcneilly.pokedex.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.adammcneilly.pokedex.databinding.FragmentPokemonDetailBinding
import com.adammcneilly.pokedex.pokeGraph

class PokemonDetailFragment : Fragment() {
    private lateinit var binding: FragmentPokemonDetailBinding
    private lateinit var viewModel: PokemonDetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val arguments: PokemonDetailFragmentArgs by navArgs()
        val pokemonName = arguments.pokemonName

        val viewModelFactory = requireContext()
            .pokeGraph()
            .viewModelFactoryGraph
            .pokemonDetailViewModelFactory(pokemonName)

        viewModel =
            ViewModelProvider(this, viewModelFactory).get(PokemonDetailViewModel::class.java)
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