package com.rohan.rohannetworking.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rohan.rohanhttp.ApiException
import com.rohan.rohanhttp.NetworkResponse
import com.rohan.rohanhttp.onError
import com.rohan.rohanhttp.onSuccess
import com.rohan.rohannetworking.models.MovieDetails
import com.rohan.rohannetworking.usecases.MovieDetailsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailsViewModel @Inject constructor(
    private val detailsUseCase: MovieDetailsUseCase
) : ViewModel() {

    private val _detailsState: MutableStateFlow<NetworkResponse<MovieDetails>?> = MutableStateFlow(null)
    val detailsState: StateFlow<NetworkResponse<MovieDetails>?> = _detailsState

    fun fetchDetails(movieId: Int) {
        viewModelScope.launch {
            _detailsState.value = detailsUseCase(movieId.toString())
        }
    }
}