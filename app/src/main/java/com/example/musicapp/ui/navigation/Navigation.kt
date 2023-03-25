package com.example.musicapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.musicapp.model.Album
import com.example.musicapp.model.AlbumsResponse
import com.example.musicapp.network.NetworkUiState
import com.example.musicapp.network.NetworkViewModel
import com.example.musicapp.ui.AlbumScreen.AlbumScreen
//import com.example.musicapp.ui.loginScreen.LoginScreen
import com.example.musicapp.ui.mainScreen.LoadingScreen
import com.example.musicapp.ui.mainScreen.MainScreen
import com.google.gson.Gson


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
            MainScreen(
                navController = navController,
                networkUiState = networkViewModel.networkUiState,
            )
        }
        composable(route = NavRoutes.Settings.name) {

        }
        composable(route = NavRoutes.Playlists.name) {

        }
        composable(route = NavRoutes.Favourites.name) {

        }
        composable(
            route = NavRoutes.Album.name + "/{album}",
            arguments = listOf(navArgument("album") {
                type = ProfileArgType()
            })
        ) { navBackStackEntry ->
            val album = navBackStackEntry.arguments?.getString("album")
                ?.let { Gson().fromJson(it, Album::class.java) } ?: throw NullPointerException()
            AlbumScreen(album = album)
        }
    }

}