package com.rohan.rohannetworking.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.rohan.rohanhttp.NetworkResponse
import com.rohan.rohannetworking.Constants.BASE_IMAGE_URL
import com.rohan.rohannetworking.R
import com.rohan.rohannetworking.models.MovieDetails
import com.rohan.rohannetworking.ui.components.MovieListItem
import com.rohan.rohannetworking.viewmodels.MovieDetailsViewModel

@Composable
fun DetailsScreen(
    modifier: Modifier = Modifier,
    viewModel: MovieDetailsViewModel = hiltViewModel(),
    movieId: Int?
) {
    val detailsState = viewModel.detailsState.collectAsState()
    LaunchedEffect(true) {
        movieId?.let {
            viewModel.fetchDetails(it)
        }
    }
    when (detailsState.value) {
        null -> CircularProgressIndicator(
            modifier = modifier
                .padding(36.dp)
                .fillMaxSize()
        )

        is NetworkResponse.Success -> {
            DetailsComposable(movieDetails = (detailsState.value as NetworkResponse.Success<MovieDetails>).data)
        }

        is NetworkResponse.Error -> {
            ErrorComposable(exception = (detailsState.value as NetworkResponse.Error).apiException)
        }
    }
}

@Composable
fun DetailsComposable(modifier: Modifier = Modifier, movieDetails: MovieDetails) {
    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
    ) {
        movieDetails.backdropPath?.let { backdropPath ->
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current).crossfade(true)
                    .data("${BASE_IMAGE_URL}${backdropPath}").build(),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                contentScale = ContentScale.Crop,
                placeholder = painterResource(id = R.drawable.outline_image_24)
            )
        }

        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = movieDetails.title,
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )

            Spacer(modifier = Modifier.height(8.dp))

            if (!movieDetails.tagline.isNullOrBlank()) {
                Text(
                    text = movieDetails.tagline ?: "",
                    style = MaterialTheme.typography.labelLarge,
                    color = Color.Gray
                )

                Spacer(modifier = Modifier.height(8.dp))
            }

            if (movieDetails.adult) {
                Image(
                    painter = painterResource(id = R.drawable.baseline_18_up_rating_24),
                    contentDescription = "18+"
                )

                Spacer(modifier = Modifier.height(16.dp))
            }

            Text(
                text = "Popularity",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.height(8.dp))
            LinearProgressIndicator(progress = movieDetails.popularity.toFloat())
            Text(text = movieDetails.popularity.toFloat().toString(), style = MaterialTheme.typography.bodyMedium)
            
            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Overview",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = movieDetails.overview,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Genres",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = movieDetails.genres.joinToString { it.name ?: "" },
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Release Date",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = movieDetails.releaseDate,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Budget",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "$${movieDetails.budget}",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface
            )

            Spacer(modifier = Modifier.height(16.dp))

            if (movieDetails.productionCompanies.isNotEmpty()) {
                Text(
                    text = "Production Companies",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.height(8.dp))
                LazyRow {
                    items(movieDetails.productionCompanies.size) {
                        MovieListItem(
                            modifier = Modifier.height(56.dp),
                            title = movieDetails.productionCompanies[it].name ?: "",
                            posterPath = movieDetails.productionCompanies[it].logoPath ?: ""
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                    }
                }
            }
            // Display other movie details here, such as genres, release date, etc.
        }
    }
}

@Composable
fun ErrorComposable(exception: Exception?, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            modifier = Modifier
                .height(72.dp)
                .width(72.dp),
            imageVector = Icons.Filled.Warning,
            contentDescription = "error",
            tint = Color.Gray
        )
        Text(text = "Could not fetch details\nFailure reasons: ${exception?.message}")
    }
}
