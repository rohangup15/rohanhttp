package com.rohan.rohanhttp

/**
 * Specification for a generic executor that makes network requests using a network client
 */
@PublishedApi
internal interface RequestExecutor {
    suspend fun executeRequest(request: NetworkRequest): ClientNetworkResponse
    fun NetworkRequest.toHttpRequest(): Any
}