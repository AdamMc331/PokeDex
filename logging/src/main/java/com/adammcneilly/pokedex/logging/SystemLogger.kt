package com.adammcneilly.pokedex.logging

class SystemLogger : Logger {
    override fun setTag(tag: String) {
        // Unnecessary here
    }

    override fun logDebug(message: String) {
        println("Debug: $message:")
    }

    override fun logError(message: String) {
        println("Error: $message")
    }

    override fun logError(error: Throwable) {
        logError(error.message.orEmpty())
    }
}