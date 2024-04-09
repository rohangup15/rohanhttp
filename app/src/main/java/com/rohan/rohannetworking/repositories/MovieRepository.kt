package com.rohan.rohannetworking.repositories

import androidx.paging.PagingData
import com.rohan.rohanhttp.NetworkResponse
import com.rohan.rohannetworking.models.Movie
import com.rohan.rohannetworking.models.MovieDetails
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    suspend fun getPopularMovies() : Flow<PagingData<Movie>>

    suspend fun getLatestMovies() : Flow<PagingData<Movie>>

    suspend fun getMovieDetails(movieId: String) : NetworkResponse<MovieDetails>
}