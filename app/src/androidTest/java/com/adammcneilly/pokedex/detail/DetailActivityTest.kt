package com.adammcneilly.pokedex.detail

import android.content.Intent
import android.view.View
import androidx.test.espresso.IdlingRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.adammcneilly.pokedex.BuildConfig
import com.adammcneilly.pokedex.R
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
class DetailActivityTest {
    @JvmField
    @Rule
    val activityTestRule = ActivityTestRule(DetailActivity::class.java, true, false)

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
        val intent = Intent().apply {
            putExtra(DetailActivity.ARG_POKEMON_NAME, testName)
        }

        mockWebServer.setDispatcher(SuccessDispatcher())
        activityTestRule.launchActivity(intent)
        progressBarGoneIdlingResource =
            ViewVisibilityIdlingResource(activityTestRule.activity.findViewById(R.id.progress_bar), View.GONE)

        DetailActivityRobot()
            .waitForCondition(progressBarGoneIdlingResource)
            .assertTitle(testName.capitalize())
            .assertFirstTypeVisible()
            .assertFirstType("Normal")
    }

    @Test
    fun displayError() {
        val testName = "ditto"
        val intent = Intent().apply {
            putExtra(DetailActivity.ARG_POKEMON_NAME, testName)
        }

        mockWebServer.setDispatcher(ErrorDispatcher())
        activityTestRule.launchActivity(intent)
        progressBarGoneIdlingResource =
            ViewVisibilityIdlingResource(activityTestRule.activity.findViewById(R.id.progress_bar), View.GONE)

        DetailActivityRobot()
            .waitForCondition(progressBarGoneIdlingResource)
            .assertErrorDisplayed()
            .assertTitle(testName.capitalize())
    }
}