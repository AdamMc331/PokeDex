package com.adammcneilly.pokedex.network.apollo

import com.adammcneilly.pokedex.core.Pokemon
import com.adammcneilly.pokedex.core.PokemonResponse
import com.adammcneilly.pokedex.core.Type
import com.adammcneilly.pokedex.network.PokemonAPI
import com.adammcneily.pokedex.PokemonDetailQuery
import com.adammcneily.pokedex.PokemonQuery
import com.adammcneily.pokedex.fragment.ApolloPokemon
import com.apollographql.apollo.api.Input
import com.apollographql.apollo.coroutines.toDeferred

class ApolloService(baseUrl: String) : PokemonAPI {
    private val apolloClient = ApolloPokemonAPI.defaultInstance(baseUrl)

    override suspend fun getPokemon(): PokemonResponse {
        val query = PokemonQuery(first = DEFAULT_LIMIT)

        val response = apolloClient.query(query).toDeferred().await()

        val pokemonList = response.data()
            ?.pokemonList
            ?.filterNotNull()
            ?.map { apolloPokemonList ->
                apolloPokemonList.fragments.apolloPokemon.toPokemon()
            }

        return PokemonResponse(
            results = pokemonList
        )
    }

    override suspend fun getPokemonDetail(pokemonName: String): Pokemon {
        val query = PokemonDetailQuery(pokemonName = Input.fromNullable(pokemonName))

        val response = apolloClient.query(query).toDeferred().await()

        return response.data()?.pokemon?.fragments?.apolloPokemon?.toPokemon() ?: Pokemon()
    }

    companion object {
        private const val DEFAULT_LIMIT = 20
    }
}

private fun ApolloPokemon.toPokemon(): Pokemon {
    return Pokemon(
        name = this.name.orEmpty(),
        firstType = Type.fromString(this.types?.getOrNull(0)),
        secondType = Type.fromString(this.types?.getOrNull(1)),
        frontSpriteUrl = this.image
    )
}