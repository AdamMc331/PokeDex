package com.adammcneilly.pokedex.main

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.IdlingResource
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.adammcneilly.pokedex.R
import com.adammcneilly.pokedex.utils.RecyclerViewMatcher

class MainActivityRobot {

    fun assertDataDisplayed() = apply {
        onView(recyclerViewMatcher).check(matches(isDisplayed()))
    }

    fun assertErrorDisplayed() = apply {
        onView(errorViewMatcher).check(matches(isDisplayed()))
    }

    fun waitForCondition(idlingResource: IdlingResource?) = apply {
        IdlingRegistry.getInstance().register(idlingResource)
    }

    fun assertPokemonAtPosition(position: Int, pokemonName: String) = apply {
        val itemMatcher = RecyclerViewMatcher(recyclerViewId)
            .atPositionOnView(position, pokemonNameId)
        onView(itemMatcher).check(matches(withText(pokemonName)))
    }

    fun clickItem(position: Int) = apply {
        val itemMatcher = RecyclerViewMatcher(recyclerViewId).atPosition(position)
        onView(itemMatcher).perform(click())
    }

    companion object {
        private const val recyclerViewId = R.id.pokemon_list
        private const val pokemonNameId = R.id.pokemon_name

        private val recyclerViewMatcher = withId(R.id.pokemon_list)
        private val errorViewMatcher = withId(R.id.error_view)
    }
}