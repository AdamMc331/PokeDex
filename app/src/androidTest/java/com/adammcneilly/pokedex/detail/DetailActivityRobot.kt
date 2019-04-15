package com.adammcneilly.pokedex.detail

import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.IdlingResource
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.hasDescendant
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.adammcneilly.pokedex.R

class DetailActivityRobot {

    fun waitForCondition(idlingResource: IdlingResource?) = apply {
        IdlingRegistry.getInstance().register(idlingResource)
    }

    fun assertErrorDisplayed() = apply {
        Espresso.onView(errorViewMatcher).check(matches(ViewMatchers.isDisplayed()))
    }

    fun assertTitle(title: String) = apply {
        onView(toolbarMatcher).check(matches(hasDescendant(withText(title))))
    }

    companion object {
        private val toolbarMatcher = withId(R.id.toolbar)
        private val errorViewMatcher = withId(R.id.error_view)
    }
}