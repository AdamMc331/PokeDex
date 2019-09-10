package com.adammcneilly.pokedex.detail

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.IdlingResource
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.hasDescendant
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import com.adammcneilly.pokedex.R

class PokemonDetailFragmentRobot {

    fun waitForCondition(idlingResource: IdlingResource?) = apply {
        IdlingRegistry.getInstance().register(idlingResource)
    }

    fun assertErrorDisplayed() = apply {
        onView(errorViewMatcher).check(matches(isDisplayed()))
    }

    fun assertTitle(title: String) = apply {
        onView(toolbarMatcher).check(matches(hasDescendant(withText(title))))
    }

    fun assertFirstTypeVisible() = apply {
        onView(firstTypeMatcher).check(matches(isDisplayed()))
    }

    fun assertFirstType(type: String) = apply {
        onView(firstTypeMatcher).check(matches(withText(type)))
    }

    companion object {
        private val toolbarMatcher = withId(R.id.toolbar)
        private val errorViewMatcher = withId(R.id.error_view)
        private val firstTypeMatcher = withId(R.id.first_type)
    }
}