package com.adammcneilly.pokedex.main

import androidx.test.espresso.IdlingRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.adammcneilly.pokedex.R
import com.adammcneilly.pokedex.ViewGoneIdlingResource
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityTest {
    @JvmField
    @Rule
    val activityTestRule = ActivityTestRule(MainActivity::class.java)

    private var progressBarIdlingResource: ViewGoneIdlingResource? = null

    @Before
    fun setup() {
        progressBarIdlingResource = ViewGoneIdlingResource(activityTestRule.activity.findViewById(R.id.progress_bar))
    }

    @After
    fun teardown() {
        IdlingRegistry.getInstance().unregister(progressBarIdlingResource)
    }

    @Test
    fun displayPokemon() {
        MainActivityRobot()
            .assertLoadingDisplayed()
            .waitForCondition(progressBarIdlingResource)
            .assertDataDisplayed()
    }
}