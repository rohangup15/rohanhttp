package com.rohan.rohannetworking

import androidx.paging.PagingData
import androidx.paging.testing.asSnapshot
import com.rohan.rohannetworking.models.Movie
import com.rohan.rohannetworking.repositories.MovieRepositoryImpl
import com.rohan.rohannetworking.usecases.LatestMoviesUseCase
import com.rohan.rohannetworking.usecases.PopularMoviesUseCase
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class LatestMoviesViewModelTest {

    @MockK
    lateinit var movieRepository: MovieRepositoryImpl

    private lateinit var latestMoviesUseCase: LatestMoviesUseCase

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        Dispatchers.setMain(testDispatcher)
        latestMoviesUseCase = LatestMoviesUseCase(movieRepository)
    }

    @Test
    fun `test whether initial fetch is success`() = runTest {
        val mockedMovieList = listOf(mockk<Movie>(), mockk<Movie>())
        val expectedMovies = PagingData.from(mockedMovieList)

        // Mock use case behavior to return a single PagingData
        coEvery { movieRepository.getLatestMovies() } returns flowOf(expectedMovies)

        // Wait for the initial collection and assert state
        advanceUntilIdle()  // Wait for coroutines launched in init block
        val result = latestMoviesUseCase()
        val actualResult = result.asSnapshot {
            scrollTo(2)
        }
        Assert.assertEquals(actualResult, mockedMovieList)
    }
}