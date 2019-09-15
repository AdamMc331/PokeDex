package com.adammcneilly.pokedex.detail

import android.os.Bundle
import android.view.View
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.IdlingRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.adammcneilly.pokedex.BuildConfig
import com.adammcneilly.pokedex.R
import com.adammcneilly.pokedex.main.MainActivity
import com.adammcneilly.pokedex.utils.ErrorDispatcher
import com.adammcneilly.pokedex.utils.SuccessDispatcher
import com.adammcneilly.pokedex.utils.ViewVisibilityIdlingResource
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Ignore
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@Ignore("Need to deep link into detail frag, tried starting it from scratch but no luck.")
@RunWith(AndroidJUnit4::class)
class PokemonDetailFragmentTest {
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
    fun displayData() {
        val testName = "ditto"
        val fragmentArgs = Bundle().apply {
            // putString(PokemonDetailFragment.ARG_POKEMON_NAME, testName)
        }

        launchFragmentInContainer<PokemonDetailFragment>(fragmentArgs)

        mockWebServer.dispatcher = SuccessDispatcher()
        progressBarGoneIdlingResource =
            ViewVisibilityIdlingResource(
                activityTestRule.activity.findViewById(R.id.progress_bar),
                View.GONE
            )

        PokemonDetailFragmentRobot()
            .waitForCondition(progressBarGoneIdlingResource)
            .assertTitle(testName.capitalize())
            .assertFirstTypeVisible()
            .assertFirstType("Normal")
    }

    @Test
    fun displayError() {
        val testName = "ditto"
        val fragmentArgs = Bundle().apply {
            // putString(PokemonDetailFragment.ARG_POKEMON_NAME, testName)
        }

        launchFragmentInContainer<PokemonDetailFragment>(fragmentArgs)

        mockWebServer.dispatcher = ErrorDispatcher()

        PokemonDetailFragmentRobot()
            .assertErrorDisplayed()
    }
}
