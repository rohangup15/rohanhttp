package com.rohan.rohannetworking.navigation

/**
 * Different navigation routes supported in the app
 * Since, we are using compose navigation, routes are in URL based format
 */
sealed class Route(val routeName: String) {
    data object Home: Route("home")
    data object Details: Route("details/{movieId}")
}