package com.rohan.rohannetworking.repositories

import androidx.paging.PagingData
import com.rohan.rohanhttp.NetworkResponse
import com.rohan.rohannetworking.models.Movie
import com.rohan.rohannetworking.models.MovieDetails
import kotlinx.coroutines.flow.Flow

/**
 * Repository interacts with the data source and exposes data in a usable format to the
 * viewModel layer
 * Can be implemented multiple data sources as an input
 */
interface MovieRepository {
    suspend fun getPopularMovies() : Flow<PagingData<Movie>>

    suspend fun getLatestMovies() : Flow<PagingData<Movie>>

    suspend fun getMovieDetails(movieId: String) : NetworkResponse<MovieDetails>
}