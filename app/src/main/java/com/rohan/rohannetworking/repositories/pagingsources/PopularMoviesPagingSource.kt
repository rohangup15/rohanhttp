package com.rohan.rohannetworking.repositories.pagingsources

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.rohan.rohanhttp.onError
import com.rohan.rohanhttp.onSuccess
import com.rohan.rohannetworking.datasources.MovieRemoteDataSource
import com.rohan.rohannetworking.models.Movie

class PopularMoviesPagingSource(
    private val remoteDataSource: MovieRemoteDataSource
) : PagingSource<Int, Movie>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        val currentPage = params.key ?: 1
        val response = remoteDataSource.getPopularMovies(currentPage)
        var loadResult: LoadResult<Int, Movie> = LoadResult.Invalid()
        response.onSuccess {
            loadResult = LoadResult.Page(
                data = it.results ?: emptyList(),
                nextKey = if (it.results.isNullOrEmpty()) null else it.page?.plus(1),
                prevKey = if (currentPage == 1) null else currentPage - 1
            )
        }.onError {
            loadResult = LoadResult.Error(it)
        }
        return loadResult
    }

    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? =
        state.anchorPosition
}