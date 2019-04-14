package com.adammcneilly.pokedex.main

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.IdlingResource
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.adammcneilly.pokedex.R

class MainActivityRobot {

    fun assertLoadingDisplayed() = apply {
        onView(progressBarMatcher).check(matches(isDisplayed()))
    }

    fun assertDataDisplayed() = apply {
        onView(recyclerViewMatcher).check(matches(isDisplayed()))
    }

    fun waitForCondition(idlingResource: IdlingResource?) = apply {
        IdlingRegistry.getInstance().register(idlingResource)
    }

    companion object {
        private val progressBarMatcher = withId(R.id.progress_bar)
        private val recyclerViewMatcher = withId(R.id.pokemon_list)
    }
}