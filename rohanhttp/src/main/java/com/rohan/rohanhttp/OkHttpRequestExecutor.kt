package com.rohan.rohanhttp

import okhttp3.HttpUrl.Companion.toHttpUrlOrNull
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.internal.EMPTY_REQUEST
import android.util.Log

/**
 * Request executor to execute network requests using [OkHttpClient]
 */
internal class OkHttpRequestExecutor(private val okHttpClient: OkHttpClient): RequestExecutor {

    override suspend fun executeRequest(request: NetworkRequest): ClientNetworkResponse {
        val response = okHttpClient.newCall(request.toHttpRequest()).execute()
        return object : ClientNetworkResponse {
            override val isSuccessful: Boolean = response.isSuccessful
            override val statusCode: Int = response.code
            override val message: String = response.message
            override val body: String? = response.body?.string()
        }
    }

    /**
     * Builds a network request from [NetworkRequest] using OkHttp's request builder
     */
    override fun NetworkRequest.toHttpRequest(): Request {
        // OkHttp's request builder object
        val builder = Request.Builder()

        val urlBuilder = url.orEmpty().toHttpUrlOrNull()?.newBuilder()

        pathSegment.forEach {
            urlBuilder?.addPathSegment(it)
        }

        // Add query parameters to URL
        query.forEach { (key, value) ->
            urlBuilder?.addQueryParameter(key, value)
        }

        // Set URL for request
        urlBuilder?.let {
            builder.url(it.build())
        }

        // Set request headers if any
        header.forEach { (key, value) ->
            builder.addHeader(key, value)
        }

        var requestBody = EMPTY_REQUEST

        // Set request body
        body?.let {
            requestBody = it.toRequestBody("application/json".toMediaTypeOrNull(), 0, it.size)
        }

        // handle request type
        when (requestType) {
            RequestType.GET -> builder.get()
            RequestType.POST -> builder.post(requestBody)
            RequestType.PUT -> builder.put(requestBody)
            RequestType.DELETE -> builder.delete(requestBody)
        }

        return builder.build()
    }
}