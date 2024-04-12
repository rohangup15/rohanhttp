package com.rohan.rohannetworking

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.google.gson.Gson
import com.rohan.rohanhttp.CustomNetworkManager
import com.rohan.rohanhttp.NetworkRequest
import com.rohan.rohanhttp.RequestType
import com.rohan.rohanhttp.onError
import com.rohan.rohanhttp.onSuccess
import com.rohan.rohannetworking.Constants.API_KEY
import com.rohan.rohannetworking.Constants.BASE_URL
import com.rohan.rohannetworking.Constants.LANGUAGE
import com.rohan.rohannetworking.models.ResponseDto
import com.rohan.rohannetworking.navigation.TmdbNavigation
import com.rohan.rohannetworking.ui.theme.RohanNetworkingTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var manager: CustomNetworkManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        logApiCall()
        setContent {
            RohanNetworkingTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    TmdbNavigation()
                }
            }
        }
    }

    private fun logApiCall() {
        GlobalScope.launch {
            val response = manager.makeCall<ResponseDto>(
                NetworkRequest(RequestType.GET)
                    .url("${BASE_URL}/popular")
                    .query(
                        mapOf(
                            "language" to LANGUAGE,
                            "page" to "1",
                            "api_key" to API_KEY
                        )
                    )
            )
            response.onSuccess {
                Log.d("test response", Gson().toJson(response))
            }.onError {
                Log.d("test response", it.toString())
            }
        }
    }
}

