package com.adammcneilly.pokedex.network.apollo

import com.adammcneilly.pokedex.core.Pokemon
import com.adammcneilly.pokedex.core.PokemonResponse
import com.adammcneilly.pokedex.core.Type
import com.adammcneilly.pokedex.network.PokemonAPI
import com.adammcneily.pokedex.PokemonDetailQuery
import com.adammcneily.pokedex.PokemonQuery
import com.apollographql.apollo.api.Input
import com.apollographql.apollo.coroutines.toDeferred

class ApolloAPI(baseUrl: String) : PokemonAPI {
    private val apolloClient = GraphQLPokemonAPI.defaultInstance(baseUrl)

    override suspend fun getPokemon(): PokemonResponse {
        val query = PokemonQuery(first = DEFAULT_LIMIT)

        val response = apolloClient.query(query).toDeferred().await()

        val pokemonList = response.data()
            ?.pokemonList
            ?.filterNotNull()
            ?.map(PokemonQuery.PokemonList::toPokemon)

        return PokemonResponse(
            results = pokemonList
        )
    }

    override suspend fun getPokemonDetail(pokemonName: String): Pokemon {
        val query = PokemonDetailQuery(pokemonName = Input.fromNullable(pokemonName))

        val response = apolloClient.query(query).toDeferred().await()

        return response.data()?.pokemon?.toPokemon() ?: Pokemon()
    }

    companion object {
        private const val DEFAULT_LIMIT = 20
    }
}

private fun PokemonQuery.PokemonList.toPokemon(): Pokemon {
    return Pokemon(
        name = this.name.orEmpty(),
        firstType = Type.fromString(this.types?.getOrNull(0)),
        secondType = Type.fromString(this.types?.getOrNull(1)),
        frontSpriteUrl = this.image
    )
}

private fun PokemonDetailQuery.Pokemon.toPokemon(): Pokemon {
    return Pokemon(
        name = this.name.orEmpty(),
        firstType = Type.fromString(this.types?.getOrNull(0)),
        secondType = Type.fromString(this.types?.getOrNull(1)),
        frontSpriteUrl = this.image
    )
}