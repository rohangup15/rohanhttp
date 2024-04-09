package com.rohan.rohannetworking.datasources

import com.rohan.rohanhttp.NetworkResponse
import com.rohan.rohannetworking.models.MovieDetails
import com.rohan.rohannetworking.models.ResponseDto

/**
 * Interface to expose fetch data from a remote data source
 * This interface can be implemented to support a range of data source such as REST-based,
 * gRPC based, DB-based, etc.
 */
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