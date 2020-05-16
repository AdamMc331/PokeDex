package com.adammcneilly.pokedex.views

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.adammcneilly.pokedex.core.Pokemon
import com.adammcneilly.pokedex.databinding.ListItemPokemonBinding
import com.adammcneilly.pokedex.viewmodels.PokemonViewModel

class PokemonAdapter(
    private val pokemonClickListener: ((Pokemon, ImageView) -> Unit)
) : RecyclerView.Adapter<PokemonAdapter.PokemonViewHolder>() {

    var items: List<Pokemon> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val binding = ListItemPokemonBinding.inflate(inflater, parent, false)
        return PokemonViewHolder(binding, pokemonClickListener)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: PokemonViewHolder, position: Int) {
        holder.bindPokemon(items[position])
    }

    class PokemonViewHolder(
        private val binding: ListItemPokemonBinding,
        pokemonClickListener: (Pokemon, ImageView) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        private val viewModel = PokemonViewModel()

        init {
            binding.viewModel = viewModel
            binding.root.setOnClickListener {
                viewModel.pokemon?.let { pokemon ->
                    pokemonClickListener.invoke(pokemon.copy(frontSpriteUrl = viewModel.imageUrl), binding.pokemonImage)
                }
            }
        }

        fun bindPokemon(pokemon: Pokemon) {
            viewModel.pokemon = pokemon
            binding.pokemonImage.transitionName = pokemon.name
            binding.executePendingBindings()
        }
    }
}