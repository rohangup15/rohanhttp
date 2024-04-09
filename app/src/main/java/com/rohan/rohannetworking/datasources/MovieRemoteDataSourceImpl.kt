package com.rohan.rohannetworking.datasources

import com.rohan.rohanhttp.CustomNetworkManager
import com.rohan.rohanhttp.NetworkRequest
import com.rohan.rohanhttp.NetworkResponse
import com.rohan.rohanhttp.RequestType
import com.rohan.rohannetworking.Constants
import com.rohan.rohannetworking.Constants.API_KEY
import com.rohan.rohannetworking.Constants.LANGUAGE
import com.rohan.rohannetworking.models.MovieDetails
import com.rohan.rohannetworking.models.ResponseDto
import javax.inject.Inject

class MovieRemoteDataSourceImpl @Inject constructor(private val manager: CustomNetworkManager) :
    MovieRemoteDataSource {
    override suspend fun getPopularMovies(
        pageNumber: Int
    ): NetworkResponse<ResponseDto> =
        manager.makeCall(
            NetworkRequest(RequestType.GET)
                .url(Constants.BASE_URL)
                .pathSegments("popular")
                .query(
                    mapOf(
                        "language" to LANGUAGE,
                        "api_key" to API_KEY,
                        "page" to pageNumber.toString()
                     )
                )
        )

    override suspend fun getLatestMovies(
        pageNumber: Int
    ): NetworkResponse<ResponseDto> =
        manager.makeCall(
            NetworkRequest(RequestType.GET)
                .url(Constants.BASE_URL)
                .pathSegments("now_playing")
                .query(
                    mapOf(
                        "language" to LANGUAGE,
                        "api_key" to API_KEY,
                        "page" to pageNumber.toString()
                    )
                )
        )

    override suspend fun getMovieDetails(movieId: String): NetworkResponse<MovieDetails> =
        manager.makeCall(
            NetworkRequest(RequestType.GET)
                .url(Constants.BASE_URL)
                .pathSegments(movieId)
                .query(
                    mapOf(
                        "language" to LANGUAGE,
                        "api_key" to API_KEY
                    )
                )
        )
}