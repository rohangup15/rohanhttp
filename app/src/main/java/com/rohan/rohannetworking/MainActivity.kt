package com.rohan.rohannetworking

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.google.gson.Gson
import com.rohan.rohanhttp.CustomNetworkManager
import com.rohan.rohanhttp.NetworkRequest
import com.rohan.rohanhttp.RequestType
import com.rohan.rohanhttp.onError
import com.rohan.rohanhttp.onSuccess
import com.rohan.rohannetworking.Constants.API_KEY
import com.rohan.rohannetworking.Constants.BASE_URL
import com.rohan.rohannetworking.Constants.LANGUAGE
import com.rohan.rohannetworking.models.Movie
import com.rohan.rohannetworking.models.ResponseDto
import com.rohan.rohannetworking.navigation.TmdbNavigation
import com.rohan.rohannetworking.ui.components.MovieListItem
import com.rohan.rohannetworking.ui.screens.PopularMoviesScreen
import com.rohan.rohannetworking.ui.screens.TabScreen
import com.rohan.rohannetworking.ui.theme.RohanNetworkingTheme
import com.rohan.rohannetworking.viewmodels.PopularMoviesViewModel
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

