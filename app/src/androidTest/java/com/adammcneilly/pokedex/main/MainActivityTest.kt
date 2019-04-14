package com.adammcneilly.pokedex.main

import android.view.View
import androidx.test.espresso.IdlingRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.adammcneilly.pokedex.R
import com.adammcneilly.pokedex.utils.ViewVisibilityIdlingResource
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

    private var progressBarGoneIdlingResource: ViewVisibilityIdlingResource? = null

    @Before
    fun setup() {
        progressBarGoneIdlingResource =
            ViewVisibilityIdlingResource(activityTestRule.activity.findViewById(R.id.progress_bar), View.GONE)
    }

    @After
    fun teardown() {
        IdlingRegistry.getInstance().unregister(progressBarGoneIdlingResource)
    }

    @Test
    fun displayPokemon() {
        MainActivityRobot()
            .waitForCondition(progressBarGoneIdlingResource)
            .assertDataDisplayed()
            .assertPokemonAtPosition(0, "Bulbasaur")
            .assertPokemonAtPosition(1, "Ivysaur")
            .assertPokemonAtPosition(2, "Venusaur")
    }
}