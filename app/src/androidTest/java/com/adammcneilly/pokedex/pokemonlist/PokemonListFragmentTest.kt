package com.adammcneilly.pokedex.pokemonlist

import android.view.View
import androidx.test.espresso.IdlingRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.adammcneilly.pokedex.BuildConfig
import com.adammcneilly.pokedex.R
import com.adammcneilly.pokedex.detail.DetailActivityRobot
import com.adammcneilly.pokedex.main.MainActivity
import com.adammcneilly.pokedex.utils.ErrorDispatcher
import com.adammcneilly.pokedex.utils.SuccessDispatcher
import com.adammcneilly.pokedex.utils.ViewVisibilityIdlingResource
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class PokemonListFragmentTest {
    @JvmField
    @Rule
    val activityTestRule = ActivityTestRule(MainActivity::class.java, true, false)

    private val mockWebServer = MockWebServer()

    private var progressBarGoneIdlingResource: ViewVisibilityIdlingResource? = null

    @Before
    fun setup() {
        mockWebServer.start(BuildConfig.PORT)
    }

    @After
    fun teardown() {
        mockWebServer.shutdown()

        IdlingRegistry.getInstance().unregister(progressBarGoneIdlingResource)
    }

    @Test
    fun displayPokemon() {
        mockWebServer.setDispatcher(SuccessDispatcher())
        activityTestRule.launchActivity(null)
        progressBarGoneIdlingResource =
            ViewVisibilityIdlingResource(
                activityTestRule.activity.findViewById(R.id.progress_bar),
                View.GONE
            )

        PokemonListRobot()
            .waitForCondition(progressBarGoneIdlingResource)
            .assertDataDisplayed()
            .assertPokemonAtPosition(0, "AdamOne")
            .assertPokemonAtPosition(1, "AdamTwo")
            .assertPokemonAtPosition(2, "AdamThree")
    }

    @Test
    fun clickItem() {
        mockWebServer.setDispatcher(SuccessDispatcher())
        activityTestRule.launchActivity(null)
        progressBarGoneIdlingResource =
            ViewVisibilityIdlingResource(
                activityTestRule.activity.findViewById(R.id.progress_bar),
                View.GONE
            )

        PokemonListRobot()
            .waitForCondition(progressBarGoneIdlingResource)
            .assertDataDisplayed()
            .clickItem(0)

        DetailActivityRobot()
            .assertTitle("AdamOne")
    }

    @Test
    fun displayError() {
        mockWebServer.setDispatcher(ErrorDispatcher())
        activityTestRule.launchActivity(null)
        progressBarGoneIdlingResource =
            ViewVisibilityIdlingResource(
                activityTestRule.activity.findViewById(R.id.progress_bar),
                View.GONE
            )

        PokemonListRobot()
            .waitForCondition(progressBarGoneIdlingResource)
            .assertErrorDisplayed()
    }
}