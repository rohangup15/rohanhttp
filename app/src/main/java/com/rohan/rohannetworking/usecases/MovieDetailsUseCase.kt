package com.rohan.rohannetworking.usecases

import com.rohan.rohanhttp.NetworkResponse
import com.rohan.rohannetworking.repositories.MovieRepository
import javax.inject.Inject

class MovieDetailsUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {
    suspend operator fun invoke(movieId: String) = movieRepository.getMovieDetails(movieId)
}