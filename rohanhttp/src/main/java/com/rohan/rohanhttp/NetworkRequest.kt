package com.rohan.rohanhttp

import org.json.JSONObject
import java.nio.charset.StandardCharsets.UTF_8

/**
 * Network request object denoting all the data required to make a network request
 * Uses builder pattern to add properties
 * @param requestType: Type of request GET, PUT, POST, DELETE
 * @property header: Request headers like content-type, authorization, etc.
 * @property query: Query parameters like BASE_URL/x?query=q
 * @property url: URL on which to make a request
 * @property body: Request body for a POST, PUT, DELETE request, denoting inputs and conditions
 * @property pathSegment: path to be added to a URL separated by a "/"
 */
class NetworkRequest(internal val requestType: RequestType) {
    internal val header: MutableMap<String, String> = HashMap()
    internal val query: MutableMap<String, String> = HashMap()
    internal var url: String? = null
    internal var body: ByteArray? = null
    internal var pathSegment: MutableList<String> = mutableListOf()

    fun url(url: String?): NetworkRequest {
        this.url = url
        return this
    }

    fun body(bodyJson: JSONObject?): NetworkRequest {
        val textBody = bodyJson?.toString()
        body = textBody?.toByteArray(charset(UTF_8.name()))
        this.header["Content-Type"] = "application/json"
        return this
    }

    fun header(header: Map<String, String>?): NetworkRequest {
        if (header.isNullOrEmpty()) return this
        this.header.putAll(header)
        return this
    }

    fun query(params: Map<String, String>?): NetworkRequest {
        if (params.isNullOrEmpty()) return this
        this.query.putAll(params)
        return this
    }

    fun pathSegments(vararg pathSegment: String): NetworkRequest {
        if (pathSegment.isEmpty()) return this
        this.pathSegment.addAll(pathSegment)
        return this
    }
}