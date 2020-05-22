package com.adammcneilly.pokedex.redux

import android.util.Log

class LoggingMiddleware : Middleware {
    override fun dispatch(action: Action, next: NextDispatcher) {
        Log.d("LoggingMiddleware", "Dispatching action: $action")
        next.dispatch(action)
    }
}