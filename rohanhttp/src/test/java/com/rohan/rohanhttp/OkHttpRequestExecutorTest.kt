package com.rohan.rohanhttp

import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.runBlocking
import okhttp3.Call
import okhttp3.OkHttpClient
import okhttp3.Protocol
import okhttp3.Request
import okhttp3.Response
import org.json.JSONObject
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import java.io.IOException

internal class OkHttpRequestExecutorTest {

    @MockK
    lateinit var okHttpClient: OkHttpClient

    @MockK
    lateinit var call: Call

    private lateinit var okHttpRequestExecutor: OkHttpRequestExecutor

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        okHttpRequestExecutor = OkHttpRequestExecutor(okHttpClient)
    }

    @Test
    fun `executeRequest returns successful ClientNetworkResponse`() = runBlocking {
        coEvery { okHttpClient.newCall(any()) } returns call
        coEvery { call.execute() } returns Response.Builder()
            .request(
                Request
                    .Builder()
                    .url("http://example.com")
                    .header("content-type", "json")
                    .build()
            )
            .protocol(Protocol.HTTP_1_1)
            .code(200)
            .message("OK")
            .build()

        val executor = OkHttpRequestExecutor(okHttpClient)

        val request = NetworkRequest(RequestType.GET)
            .url("http://example.com")
            .header(mapOf("content-type" to "json"))

        val response = executor.executeRequest(request)

        assertEquals(true, response.isSuccessful)
        assertEquals(200, response.statusCode)
        assertEquals("OK", response.message)
    }
}