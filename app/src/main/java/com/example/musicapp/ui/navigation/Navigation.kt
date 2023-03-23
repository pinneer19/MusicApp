package com.example.musicapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.musicapp.network.NetworkViewModel
//import com.example.musicapp.ui.loginScreen.LoginScreen
import com.example.musicapp.ui.mainScreen.LoadingScreen
import com.example.musicapp.ui.mainScreen.MainScreen


@Composable
fun Navigation(
    navController: NavHostController,
    networkViewModel: NetworkViewModel,
    modifier: Modifier = Modifier,
    ) {

    NavHost(
        navController = navController,
        startDestination = NavRoutes.Start.name
    )
    {
        composable(route = NavRoutes.Login.name) {
            //LoginScreen()
        }
        composable(route = NavRoutes.Start.name) {
            //val networkViewModel: NetworkViewModel = viewModel()
            MainScreen(networkViewModel.networkUiState,)

        }
        composable(route = NavRoutes.Settings.name) {

        }
        composable(route = NavRoutes.Playlists.name) {

        }
        composable(route = NavRoutes.Favourites.name) {

        }
    }

}