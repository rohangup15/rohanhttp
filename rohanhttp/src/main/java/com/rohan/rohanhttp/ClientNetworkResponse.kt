package com.rohan.rohanhttp

@PublishedApi
internal interface ClientNetworkResponse {
    val isSuccessful: Boolean
    val statusCode: Int
    val message: String?
    val body: String?
}