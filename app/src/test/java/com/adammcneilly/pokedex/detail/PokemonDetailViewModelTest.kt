package com.adammcneilly.pokedex.detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.adammcneilly.pokedex.CoroutinesTestRule
import com.adammcneilly.pokedex.R
import com.adammcneilly.pokedex.core.Pokemon
import com.adammcneilly.pokedex.core.Type
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Ignore
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
    fun showLoadingBeforeData() {
        val testName = "Adam"
        val testPokemon = Pokemon(name = testName, firstType = Type.GRASS)

        testRobot
            .setInitialPokemonName(testName)
            .buildViewModel()
            .assertShowLoading(true)
            .assertShowData(false)
            .assertShowError(false)

        testRobot
            .mockPokemonDetails(testPokemon)
            .assertShowData(true)
            .assertShowLoading(false)
            .assertShowError(false)
    }

    @Ignore
    @Test
    fun showLoadingBeforeError() {
        val testName = "Adam"

        testRobot
            .setInitialPokemonName(testName)
            .buildViewModel()
            .assertShowLoading(true)
            .assertShowData(false)
            .assertShowError(false)

        testRobot
            .mockPokemonDetailsError()
            .assertShowData(false)
            .assertShowLoading(false)
            .assertShowError(true)
    }

    @Test
    fun loadData() {
        val testName = "Adam"
        val testPokemon = Pokemon(name = testName, firstType = Type.GRASS)

        testRobot
            .setInitialPokemonName(testName)
            .buildViewModel()
            .mockPokemonDetails(testPokemon)
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

        testRobot
            .buildViewModel()
            .mockPokemonDetails(testPokemon)
            .assertFirstType(Type.UNKNOWN)
            .assertSecondType(null)
            .assertShowFirstType(true)
            .assertShowSecondType(false)
    }

    @Test
    fun getTypesWithOneType() {
        val firstType = Type.GRASS
        val testPokemon = Pokemon(
            firstType = firstType
        )

        testRobot
            .buildViewModel()
            .mockPokemonDetails(testPokemon)
            .assertFirstType(firstType)
            .assertSecondType(null)
            .assertShowFirstType(true)
            .assertShowSecondType(false)
    }

    @Test
    fun getTypesWithTwoTypes() {
        runBlocking {
            val firstType = Type.GRASS
            val secondType = Type.BUG
            val testPokemon = Pokemon(
                firstType = firstType,
                secondType = secondType
            )

            testRobot
                .buildViewModel()
                .mockPokemonDetails(testPokemon)
                .assertFirstType(firstType)
                .assertSecondType(secondType)
                .assertShowFirstType(true)
                .assertShowSecondType(true)
        }
    }

    @Ignore
    @Test
    fun loadError() {
        testRobot
            .buildViewModel()
            .mockPokemonDetailsError()
            .assertShowLoading(false)
            .assertShowData(false)
            .assertShowError(true)
    }
}