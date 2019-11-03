package com.adammcneilly.pokedex.models

import com.adammcneilly.pokedex.network.models.PokemonDTO
import com.adammcneilly.pokedex.network.models.PokemonResponseDTO
import junit.framework.Assert.assertEquals
import org.junit.Test

class PokemonResponseTest {

    @Test
    fun mapFromPokemonResponseDTO() {
        val testCount = 5
        val testNext = "Next"
        val testPrevious = "Previous"
        val testResults = listOf(
            PokemonDTO(
                name = "Testing"
            )
        )
        val pokemonResponseDTO = PokemonResponseDTO(
            count = testCount,
            next = testNext,
            previous = testPrevious,
            results = testResults
        )

        val pokemonResponse = pokemonResponseDTO.toPokemonResponse()
        assertEquals(testCount, pokemonResponse.count)
        assertEquals(testNext, pokemonResponse.next)
        assertEquals(testPrevious, pokemonResponse.previous)
        assertEquals(testResults.map(PokemonDTO::toPokemon), pokemonResponse.results)
    }
}