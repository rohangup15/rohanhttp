package com.rohan.rohannetworking.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.rohan.rohannetworking.models.Movie
import com.rohan.rohannetworking.ui.components.MovieListItem
import com.rohan.rohannetworking.viewmodels.LatestMoviesViewModel
import com.rohan.rohannetworking.viewmodels.PopularMoviesViewModel

@Composable
fun LatestMoviesScreen(
    modifier: Modifier = Modifier,
    viewModel: LatestMoviesViewModel = hiltViewModel(),
    navController: NavController
) {
    val moviePagingItems: LazyPagingItems<Movie> =
        viewModel.latestMoviesState.collectAsLazyPagingItems()
    LazyVerticalGrid(
        modifier = modifier
            .padding(8.dp)
            .fillMaxSize(),
        columns = GridCells.Fixed(3),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(moviePagingItems.itemCount) { index ->
            moviePagingItems[index]?.let {
                MovieListItem(
                    modifier = Modifier.wrapContentSize(),
                    title = it.title ?: "",
                    posterPath = it.posterPath ?: ""
                )
            }
        }
        moviePagingItems.apply {
            when {
                loadState.refresh is LoadState.Loading -> {
                    item { CircularProgressIndicator(modifier = Modifier.wrapContentSize()) }
                }

                loadState.refresh is LoadState.Error -> {
                    val error = moviePagingItems.loadState.refresh as LoadState.Error
                    item {
                        TextButton(
                            modifier = Modifier.fillMaxWidth(),
                            content = { Text(text = "Loading Error") },
                            onClick = {
                                retry()
                            }
                        )
                    }
                }

                loadState.append is LoadState.Loading -> {
                    item { CircularProgressIndicator(modifier = Modifier.wrapContentSize()) }
                }

                loadState.append is LoadState.Error -> {
                    val error = moviePagingItems.loadState.append as LoadState.Error
                    item {
                        TextButton(
                            modifier = Modifier.fillMaxWidth(),
                            content = { Text(text = "Loading Error") },
                            onClick = {
                                retry()
                            }
                        )
                    }
                }
            }
        }
    }
}