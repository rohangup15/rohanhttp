package com.rohan.rohannetworking

import com.rohan.rohanhttp.ApiException
import com.rohan.rohanhttp.NetworkResponse
import com.rohan.rohannetworking.models.MovieDetails
import com.rohan.rohannetworking.usecases.MovieDetailsUseCase
import com.rohan.rohannetworking.viewmodels.MovieDetailsViewModel
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class MovieDetailsViewModelTest {

    @MockK
    lateinit var movieDetailsUseCase: MovieDetailsUseCase

    private lateinit var movieDetailsViewModel: MovieDetailsViewModel

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        Dispatchers.setMain(testDispatcher)
        movieDetailsViewModel = MovieDetailsViewModel(movieDetailsUseCase)
    }

    @Test
    fun `test whether fetch details updates the state flow on success response`() = runTest {
        val dummyResponse = NetworkResponse.Success(
            data = mockk<MovieDetails>()
        )
        coEvery { movieDetailsUseCase("0") } returns dummyResponse
        movieDetailsViewModel.fetchDetails(0)
        advanceUntilIdle()
        Assert.assertEquals(dummyResponse, movieDetailsViewModel.detailsState.value)
    }

    @Test
    fun `test whether fetch details updates the state flow on error response`() = runTest {
        val dummyResponse = NetworkResponse.Error(
            apiException = mockk<ApiException>()
        )
        coEvery { movieDetailsUseCase("0") } returns dummyResponse
        movieDetailsViewModel.fetchDetails(0)
        advanceUntilIdle()
        Assert.assertEquals(dummyResponse, movieDetailsViewModel.detailsState.value)
    }

}