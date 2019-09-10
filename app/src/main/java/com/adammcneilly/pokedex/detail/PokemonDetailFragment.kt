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
import com.adammcneilly.pokedex.PokeApp
import com.adammcneilly.pokedex.databinding.FragmentPokemonDetailBinding
import com.adammcneilly.pokedex.network.PokemonAPI
import com.adammcneilly.pokedex.network.PokemonRetrofitService

class PokemonDetailFragment : Fragment() {
    private lateinit var binding: FragmentPokemonDetailBinding
    private lateinit var viewModel: PokemonDetailViewModel

    private val viewModelFactory = object : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            val pokemonAPI =
                PokemonAPI.defaultInstance((activity?.application as? PokeApp)?.baseUrl.orEmpty())
            val repository = PokemonRetrofitService(pokemonAPI)
            val pokemonName = arguments?.getString(ARG_POKEMON_NAME).orEmpty()

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

    companion object {
        const val ARG_POKEMON_NAME = "pokemonName"

        fun newInstance(pokemonName: String): PokemonDetailFragment {
            return PokemonDetailFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_POKEMON_NAME, pokemonName)
                }
            }
        }
    }
}
