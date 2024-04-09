package com.rohan.rohanhttp

import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract

/**
 * A generic network response blueprint, response type is passed by the calling app
 */
sealed class NetworkResponse<out T> {
    data class Success<out T>(val data: T) : NetworkResponse<T>()
    data class Error(val apiException: ApiException) : NetworkResponse<Nothing>()
}

@OptIn(ExperimentalContracts::class)
fun <T> NetworkResponse<T>.onSuccess(block: (T) -> Unit): NetworkResponse<T> {
    contract { callsInPlace(block, InvocationKind.AT_MOST_ONCE) }
    if (this is NetworkResponse.Success) {
        block(data)
    }
    return this
}

@OptIn(ExperimentalContracts::class)
fun NetworkResponse<*>.onError(block: (ApiException) -> Unit): NetworkResponse<*> {
    contract { callsInPlace(block, InvocationKind.AT_MOST_ONCE) }
    if (this is NetworkResponse.Error) {
        block(apiException)
    }
    return this
}


/**
 * Generic exception holder for API errors
 */
data class ApiException (
    override val message: String,
    val statusCode: Int = 0
) : Exception(message)