package com.adammcneilly.pokedex

import android.view.View
import androidx.test.espresso.IdlingResource

class ViewGoneIdlingResource(
    private val view: View
) : IdlingResource {
    private var resourceCallback: IdlingResource.ResourceCallback? = null
    private var isIdle: Boolean = false

    override fun getName(): String {
        return ViewGoneIdlingResource::class.java.name
    }

    override fun isIdleNow(): Boolean {
        if (isIdle) return true

        isIdle = view.visibility == View.GONE

        if (isIdle) {
            resourceCallback?.onTransitionToIdle()
        }

        return isIdle
    }

    override fun registerIdleTransitionCallback(callback: IdlingResource.ResourceCallback?) {
        this.resourceCallback = callback
    }
}