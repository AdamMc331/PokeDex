package com.adammcneilly.pokedex.models

import com.adammcneilly.pokedex.network.models.PokemonDTO
import com.adammcneilly.pokedex.network.models.PokemonResponseDTO

data class PokemonResponse(
    val count: Int? = null,
    val next: String? = null,
    val previous: String? = null,
    val results: List<Pokemon>? = null
)

fun PokemonResponseDTO.toPokemonResponse(): PokemonResponse {
    return PokemonResponse(
        count = this.count,
        next = this.next,
        previous = this.previous,
        results = this.results?.mapNotNull(PokemonDTO::toPokemon)
    )
}