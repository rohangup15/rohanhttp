package com.rohan.rohannetworking.repositories

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.rohan.rohanhttp.NetworkResponse
import com.rohan.rohannetworking.datasources.MovieRemoteDataSource
import com.rohan.rohannetworking.models.Movie
import com.rohan.rohannetworking.models.MovieDetails
import com.rohan.rohannetworking.repositories.pagingsources.LatestMoviesPagingSource
import com.rohan.rohannetworking.repositories.pagingsources.PopularMoviesPagingSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(private val remoteDataSource: MovieRemoteDataSource) : MovieRepository {
    override suspend fun getPopularMovies(): Flow<PagingData<Movie>> =
        Pager(
            config = PagingConfig(
                pageSize = 10,
                prefetchDistance = 2
            ),
            pagingSourceFactory = {
                PopularMoviesPagingSource(remoteDataSource)
            }
        ).flow

    override suspend fun getLatestMovies(): Flow<PagingData<Movie>> =
        Pager(
            config = PagingConfig(
                pageSize = 10,
                prefetchDistance = 2
            ),
            pagingSourceFactory = {
                LatestMoviesPagingSource(remoteDataSource)
            }
        ).flow

    override suspend fun getMovieDetails(movieId: String): NetworkResponse<MovieDetails> =
        remoteDataSource.getMovieDetails(movieId)

}