package com.rohan.rohanhttp

import android.util.Log
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient

/**
 * Network manager exposed to a client app, handles making network requests and returning appropriate
 * response/error
 * @param clientType: Type of client used to make an API call, as of now only OkHttp is supported,
 * can be extended to support other clients and gRPC as well
 */
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
                    try {
                        val parsedResponse = parseResponseBody<T>(responseBody)
                        NetworkResponse.Success(parsedResponse)
                    } catch (e: Exception) {
                        NetworkResponse.Error(ApiException("Error while parsing the response", 422))
                    }
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