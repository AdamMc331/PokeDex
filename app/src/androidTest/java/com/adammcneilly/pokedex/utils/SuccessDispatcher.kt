package com.adammcneilly.pokedex.utils

import android.content.Context
import android.net.Uri
import androidx.test.platform.app.InstrumentationRegistry
import com.adammcneilly.pokedex.utils.AssetReaderUtil.asset
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.RecordedRequest

class SuccessDispatcher(
    private val context: Context = InstrumentationRegistry.getInstrumentation().targetContext
) : Dispatcher() {
    private val responseFilesByPath: Map<String, String> = mapOf(
        APIPaths.POKEMON_LIST to MockFiles.POKEMON_LIST_SUCCESS,
        APIPaths.POKEMON_DITTO to MockFiles.POKEMON_DETAIL_SUCCESS
    )

    override fun dispatch(request: RecordedRequest?): MockResponse {
        val errorResponse = MockResponse().setResponseCode(404)

        val pathWithoutQueryParams = Uri.parse(request?.path).path ?: return errorResponse
        val responseFile = responseFilesByPath[pathWithoutQueryParams]

        return if (responseFile != null) {
            val responseBody = asset(context, responseFile)
            MockResponse().setResponseCode(200).setBody(responseBody)
        } else {
            errorResponse
        }
    }
}