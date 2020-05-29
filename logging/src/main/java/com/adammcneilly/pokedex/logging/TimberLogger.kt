package com.adammcneilly.pokedex.logging

import timber.log.Timber

class TimberLogger : Logger {
    override fun setTag(tag: String) {
        // Not needed
    }

    override fun logDebug(message: String) {
        Timber.d(message)
    }

    override fun logError(message: String) {
        Timber.e(message)
    }

    override fun logError(error: Throwable) {
        Timber.e(error)
    }
}