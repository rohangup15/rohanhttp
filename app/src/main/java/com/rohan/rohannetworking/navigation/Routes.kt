package com.rohan.rohannetworking.navigation

sealed class Route(val routeName: String) {
    data object Home: Route("home")
    data object Details: Route("details/{movieId}")
}