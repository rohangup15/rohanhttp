package com.rohan.rohanhttp

/**
 * Generic network response object from any network client
 */
@PublishedApi
internal interface ClientNetworkResponse {
    val isSuccessful: Boolean
    val statusCode: Int
    val message: String?
    val body: String?
}