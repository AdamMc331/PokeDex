package com.adammcneilly.pokedex.network.apollo

import android.annotation.SuppressLint
import com.adammcneilly.pokedex.core.Pokemon
import com.adammcneilly.pokedex.core.PokemonResponse
import com.adammcneilly.pokedex.core.Type
import com.adammcneilly.pokedex.network.PokemonAPI
import com.adammcneily.pokedex.PokemonDetailQuery
import com.adammcneily.pokedex.PokemonQuery
import com.adammcneily.pokedex.fragment.ApolloPokemon
import com.apollographql.apollo.api.Input
import com.apollographql.apollo.coroutines.toDeferred
import com.apollographql.apollo.coroutines.toFlow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ApolloService(baseUrl: String) : PokemonAPI {
    private val apolloClient = ApolloPokemonAPI.defaultInstance(baseUrl)

    override suspend fun getPokemon(): PokemonResponse {
        val query = PokemonQuery(first = DEFAULT_LIMIT)

        val response = apolloClient.query(query).toDeferred().await()

        val pokemonList = response.data
            ?.pokemonList
            ?.filterNotNull()
            ?.map { apolloPokemonList ->
                apolloPokemonList.fragments.apolloPokemon.toPokemon()
            }

        return PokemonResponse(
            results = pokemonList
        )
    }

    override fun getPokemonDetailFlow(pokemonName: String): Flow<Pokemon> {
        val query = PokemonDetailQuery(pokemonName = Input.fromNullable(pokemonName))

        val apolloResponse = apolloClient.query(query).toFlow()

        return apolloResponse.map { response ->
            response.data?.pokemon?.fragments?.apolloPokemon?.toPokemon() ?: Pokemon()
        }
    }

    companion object {
        private const val DEFAULT_LIMIT = 20
    }
}

@SuppressLint("DefaultLocale")
private fun ApolloPokemon.toPokemon(): Pokemon {
    return Pokemon(
        name = this.name.orEmpty(),
        firstType = Type.fromString(this.types?.getOrNull(0)?.toUpperCase()),
        secondType = Type.fromString(this.types?.getOrNull(1)?.toUpperCase()),
        frontSpriteUrl = this.image
    )
}