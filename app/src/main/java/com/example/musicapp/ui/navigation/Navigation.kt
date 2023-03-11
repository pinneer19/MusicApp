package com.example.musicapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.musicapp.ui.mainScreen.MainScreen


@Composable
fun Navigation(
    navController: NavHostController,
    modifier: Modifier = Modifier,

    ) {

    NavHost(
        navController = navController,
        startDestination = NavRoutes.Start.name
    )
    {
        composable(route = NavRoutes.Start.name) {
            MainScreen()
        }
        composable(route = NavRoutes.Settings.name) {

        }
        composable(route = NavRoutes.Playlists.name) {

        }
        composable(route = NavRoutes.Favourites.name) {

        }
    }

}