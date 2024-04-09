package com.rohan.rohannetworking.datasources

import com.rohan.rohanhttp.NetworkResponse
import com.rohan.rohannetworking.models.MovieDetails
import com.rohan.rohannetworking.models.ResponseDto

interface MovieRemoteDataSource {
    suspend fun getPopularMovies(
        pageNumber: Int
    ) : NetworkResponse<ResponseDto>

    suspend fun getLatestMovies(
        pageNumber: Int
    ) : NetworkResponse<ResponseDto>

    suspend fun getMovieDetails(
        movieId: String
    ) : NetworkResponse<MovieDetails>
}