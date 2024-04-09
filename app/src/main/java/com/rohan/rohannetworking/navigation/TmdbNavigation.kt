package com.rohan.rohannetworking.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.rohan.rohannetworking.ui.screens.DetailsScreen
import com.rohan.rohannetworking.ui.screens.HomeScreen

@Composable
fun TmdbNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Route.Home.routeName) {
        composable(Route.Home.routeName) {
            HomeScreen(navController)
        }
        composable(
            Route.Details.routeName,
            arguments = listOf(navArgument("movieId") { type = NavType.IntType })
        ) {
            DetailsScreen(movieId = it.arguments?.getInt("movieId"))
        }
    }
}