package com.rohan.rohannetworking.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.rohan.rohannetworking.models.Movie
import com.rohan.rohannetworking.usecases.LatestMoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LatestMoviesViewModel @Inject constructor(
    private val latestMoviesUseCase: LatestMoviesUseCase
) : ViewModel() {

    private val _latestMoviesState: MutableStateFlow<PagingData<Movie>> = MutableStateFlow(
        PagingData.empty()
    )

    val latestMoviesState: StateFlow<PagingData<Movie>> = _latestMoviesState

    init {
        viewModelScope.launch {
            getPopularMovies()
        }
    }

    private suspend fun getPopularMovies() = latestMoviesUseCase()
        .distinctUntilChanged()
        .cachedIn(viewModelScope)
        .collect {
            _latestMoviesState.value = it
        }
}