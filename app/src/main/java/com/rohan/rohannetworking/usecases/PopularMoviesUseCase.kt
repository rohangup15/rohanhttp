package com.rohan.rohannetworking.usecases

import androidx.paging.PagingData
import com.rohan.rohannetworking.models.Movie
import com.rohan.rohannetworking.repositories.MovieRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PopularMoviesUseCase @Inject constructor(private  val movieRepository: MovieRepository) {
    suspend operator fun invoke(): Flow<PagingData<Movie>> = movieRepository.getPopularMovies()
}