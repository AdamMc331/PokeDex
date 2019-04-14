package com.adammcneilly.pokedex.views

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.adammcneilly.pokedex.databinding.ListItemPokemonBinding
import com.adammcneilly.pokedex.models.Pokemon
import com.adammcneilly.pokedex.viewmodels.PokemonViewModel

class PokemonAdapter : RecyclerView.Adapter<PokemonAdapter.PokemonViewHolder>() {

    var items: List<Pokemon> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonAdapter.PokemonViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val binding = ListItemPokemonBinding.inflate(inflater, parent, false)
        return PokemonViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: PokemonAdapter.PokemonViewHolder, position: Int) {
        holder.bindPokemon(items[position])
    }

    class PokemonViewHolder(private val binding: ListItemPokemonBinding) : RecyclerView.ViewHolder(binding.root) {
        private val viewModel = PokemonViewModel()

        init {
            binding.viewModel = viewModel
        }

        fun bindPokemon(pokemon: Pokemon) {
            viewModel.pokemon = pokemon
            binding.executePendingBindings()
        }
    }
}