package com.rohan.rohannetworking

import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import androidx.paging.testing.TestPager
import com.rohan.rohanhttp.NetworkResponse
import com.rohan.rohannetworking.datasources.MovieRemoteDataSource
import com.rohan.rohannetworking.models.Movie
import com.rohan.rohannetworking.models.ResponseDto
import com.rohan.rohannetworking.repositories.pagingsources.PopularMoviesPagingSource
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class PopularMoviesPageSourceTest {


    private lateinit var popularMoviesPagingSource: PopularMoviesPagingSource

    @MockK
    lateinit var movieRemoteDataSource: MovieRemoteDataSource

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        Dispatchers.setMain(testDispatcher)
        popularMoviesPagingSource = PopularMoviesPagingSource(movieRemoteDataSource)
    }

    @Test
    fun `test whether getPopularMovies returns valid data`() = runTest {
        val mockResults = listOf(
            Movie(
               0,
                false,
                null,
                emptyList(),
                null,
                null,
                null,
                0.0,
                null,
                null,
                null,
                false,
                0.0,
                0
            ),
            Movie(
                1,
                false,
                null,
                emptyList(),
                null,
                null,
                null,
                0.0,
                null,
                null,
                null,
                false,
                0.0,
                0
            )
        )
        val mockedMovieResponse = NetworkResponse.Success(data = ResponseDto(results = mockResults, page = 1, totalPages = 1, totalResults = 2))
        val pager = TestPager(
            PagingConfig(
                10,
                2,
            ),
            popularMoviesPagingSource
        )

        coEvery {
            movieRemoteDataSource.getPopularMovies(any<Int>())
        } returns mockedMovieResponse

        val result = pager.refresh() as PagingSource.LoadResult.Page
        advanceUntilIdle()
        assertEquals(mockedMovieResponse.data.results?.size, result.data.size)
        assertTrue(result.contains(mockResults[0]))
        assertTrue(result.contains(mockResults[1]))
    }
}