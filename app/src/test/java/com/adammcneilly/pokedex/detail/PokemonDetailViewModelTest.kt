package com.adammcneilly.pokedex.detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.adammcneilly.pokedex.CoroutinesTestRule
import com.adammcneilly.pokedex.R
import com.adammcneilly.pokedex.models.Pokemon
import com.adammcneilly.pokedex.models.Type
import com.adammcneilly.pokedex.models.TypeSlot
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
@Suppress("UNCHECKED_CAST")
class PokemonDetailViewModelTest {
    @JvmField
    @Rule
    val instantTaskExecutor = InstantTaskExecutorRule()

    @JvmField
    @Rule
    val coroutinesTestRule = CoroutinesTestRule()

    private val testRobot = PokemonDetailViewModelRobot()

    @Test
    fun loadData() {
        val testName = "Adam"
        val testPokemon = Pokemon(name = testName, types = listOf(TypeSlot(type = Type("grass"))))

        testRobot.mockPokemonDetails(testPokemon)
            .setInitialPokemonName(testName)
            .buildViewModel()
            .assertShowLoading(false)
            .assertShowError(false)
            .assertShowData(true)
            .assertTitle(testName.capitalize())
            .assertToolbarColorRes(R.color.type_grass)
            .assertToolbarTextColorRes(R.color.mds_white)
    }

    @Test
    fun getTypesWithNoTypes() {
        val testPokemon = Pokemon()

        testRobot.mockPokemonDetails(testPokemon)
            .buildViewModel()
            .assertFirstType(null)
            .assertSecondType(null)
            .assertShowFirstType(false)
            .assertShowSecondType(false)
    }

    @Test
    fun getTypesWithOneType() {
        val firstType = Type("grass")
        val testPokemon = Pokemon(
            types = listOf(TypeSlot(slot = 1, type = firstType))
        )

        testRobot.mockPokemonDetails(testPokemon)
            .buildViewModel()
            .assertFirstType(firstType)
            .assertSecondType(null)
            .assertShowFirstType(true)
            .assertShowSecondType(false)
    }

    @Test
    fun getTypesWithTwoTypes() {
        runBlocking {
            val firstType = Type("grass")
            val secondType = Type("bug")
            val testPokemon = Pokemon(
                types = listOf(
                    TypeSlot(slot = 1, type = firstType),
                    TypeSlot(slot = 2, type = secondType)
                )
            )

            testRobot.mockPokemonDetails(testPokemon)
                .buildViewModel()
                .assertFirstType(firstType)
                .assertSecondType(secondType)
                .assertShowFirstType(true)
                .assertShowSecondType(true)
        }
    }

    @Test
    fun loadError() {
        testRobot.mockPokemonDetailsError()
            .buildViewModel()
            .assertShowLoading(false)
            .assertShowData(false)
            .assertShowError(true)
    }
}