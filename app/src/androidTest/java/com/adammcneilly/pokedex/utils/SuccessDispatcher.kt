package com.adammcneilly.pokedex.utils

import android.content.Context
import android.net.Uri
import com.adammcneilly.pokedex.TestApplication
import com.adammcneilly.pokedex.utils.AssetReaderUtil.asset
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.RecordedRequest

class SuccessDispatcher(
    private val context: Context = TestApplication.sInstance
) : Dispatcher() {
    private val responseFilesByPath: Map<String, String> = mapOf(
        APIPaths.POKEMON_LIST to MockFiles.POKEMON_LIST_SUCCESS
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