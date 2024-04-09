package com.rohan.rohanhttp

@PublishedApi
internal interface RequestExecutor {
    suspend fun executeRequest(request: NetworkRequest): ClientNetworkResponse
    fun NetworkRequest.toHttpRequest(): Any
}