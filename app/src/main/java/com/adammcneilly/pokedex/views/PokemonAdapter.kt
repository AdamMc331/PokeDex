package com.adammcneilly.pokedex.views

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.adammcneilly.pokedex.core.Pokemon
import com.adammcneilly.pokedex.databinding.PokemonCardViewBinding
import com.adammcneilly.pokedex.viewmodels.PokemonViewModel

class PokemonAdapter(
    private val pokemonClickListener: ((Pokemon) -> Unit)
) : RecyclerView.Adapter<PokemonAdapter.PokemonViewHolder>() {

    var items: List<Pokemon> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val binding = PokemonCardViewBinding.inflate(inflater, parent, false)
        return PokemonViewHolder(binding, pokemonClickListener)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: PokemonViewHolder, position: Int) {
        holder.bindPokemon(items[position])
    }

    class PokemonViewHolder(
        private val binding: PokemonCardViewBinding,
        pokemonClickListener: (Pokemon) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        private val viewModel = PokemonViewModel()

        init {
            binding.viewModel = viewModel
            binding.root.setOnClickListener {
                viewModel.pokemon?.let(pokemonClickListener::invoke)
            }
        }

        fun bindPokemon(pokemon: Pokemon) {
            viewModel.pokemon = pokemon
            binding.executePendingBindings()
        }
    }
}