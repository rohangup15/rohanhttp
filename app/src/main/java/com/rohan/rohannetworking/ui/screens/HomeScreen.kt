package com.rohan.rohannetworking.ui.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun HomeScreen(
    navController: NavController
) {
    Scaffold(
        topBar = {
            Text(
                modifier = Modifier.padding(top = 8.dp, start = 8.dp),
                text = "TheMovieDB App",
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Black
            )
        }
    ) {
        TabScreen(
            modifier = Modifier.padding(it),
            navController
        )
    }
}