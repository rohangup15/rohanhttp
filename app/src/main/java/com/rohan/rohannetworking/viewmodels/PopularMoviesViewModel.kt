package com.rohan.rohannetworking.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.rohan.rohannetworking.models.Movie
import com.rohan.rohannetworking.usecases.PopularMoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PopularMoviesViewModel @Inject constructor(
    private val popularMoviesUseCase: PopularMoviesUseCase
) : ViewModel() {

    private val _popularMoviesState: MutableStateFlow<PagingData<Movie>> = MutableStateFlow(
        PagingData.empty()
    )

    val popularMoviesState: StateFlow<PagingData<Movie>> = _popularMoviesState

    init {
        viewModelScope.launch {
            getPopularMovies()
        }
    }

    private suspend fun getPopularMovies() = popularMoviesUseCase()
        .distinctUntilChanged()
        .cachedIn(viewModelScope)
        .collect {
            _popularMoviesState.value = it
        }
}