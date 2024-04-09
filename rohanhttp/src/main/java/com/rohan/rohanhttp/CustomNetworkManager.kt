package com.rohan.rohanhttp

import android.util.Log
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient

class CustomNetworkManager(clientType: NetworkClientType) {

    @PublishedApi
    internal var requestExecutor: RequestExecutor

    init {
        requestExecutor = when (clientType) {
            NetworkClientType.OKHTTP_CLIENT -> OkHttpRequestExecutor(OkHttpClient())
        }
    }

    suspend inline fun <reified T> makeCall(request: NetworkRequest): NetworkResponse<T> {
        return withContext(Dispatchers.IO) {
            try {
                val response = requestExecutor.executeRequest(request)

                if (response.isSuccessful) {
                    val responseBody = response.body
                    val parsedResponse = parseResponseBody<T>(responseBody)
                    NetworkResponse.Success(parsedResponse)
                } else {
                    NetworkResponse.Error(ApiException(response.message ?: "Internal Error", response.statusCode))
                }
            } catch (e: Exception) {
                NetworkResponse.Error(ApiException("Network Error", 504))
            }
        }
    }

    @PublishedApi
    internal inline fun <reified T> parseResponseBody(responseBody: String?): T {
        return Gson().fromJson(responseBody, T::class.java)
    }
}