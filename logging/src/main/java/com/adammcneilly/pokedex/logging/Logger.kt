package com.adammcneilly.pokedex.logging

/**
 * Defines the contract for any implementations to log information about our application
 * (or its modules).
 */
interface Logger {
    /**
     * For some implementations, such as [AndroidLogger], we may want to specify a tag
     * for any subsequent log statements, so we provide a public way to modify that.
     */
    fun setTag(tag: String)

    fun logDebug(message: String)
    fun logError(message: String)
    fun logError(error: Throwable)
}