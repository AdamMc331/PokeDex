package com.adammcneilly.pokedex.data

import com.adammcneilly.pokedex.core.Pokemon
import com.adammcneilly.pokedex.core.PokemonResponse
import com.dropbox.android.external.store4.StoreResponse
import kotlinx.coroutines.flow.Flow

interface PokemonRepository {
    fun getPokemon(): Flow<Result<PokemonResponse>>
    fun getPokemonDetailFromStore(pokemonName: String): Flow<StoreResponse<Pokemon>>
}