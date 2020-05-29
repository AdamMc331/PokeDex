package com.adammcneilly.pokedex.logging

import android.annotation.SuppressLint
import android.util.Log

@SuppressLint("LogNotTimber")
class AndroidLogger : Logger {
    private var tag: String = AndroidLogger::class.java.simpleName

    override fun setTag(tag: String) {
        this.tag = tag
    }

    override fun logDebug(message: String) {
        Log.d(tag, message)
    }

    override fun logError(message: String) {
        Log.e(tag, message)
    }

    override fun logError(error: Throwable) {
        Log.e(tag, error.message.orEmpty(), error)
    }
}