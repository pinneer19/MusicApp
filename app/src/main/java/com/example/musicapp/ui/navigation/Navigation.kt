package com.example.musicapp.ui.navigation

//import com.example.musical.ui.loginScreen.LoginScreen
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.musicapp.model.TrackResponse
import com.example.musicapp.network.NetworkUiState
import com.example.musicapp.network.NetworkViewModel
import com.example.musicapp.ui.AlbumScreen.AlbumScreen
import com.example.musicapp.ui.mainScreen.LoadingScreen
import com.example.musicapp.ui.mainScreen.MainScreen
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


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
                type = NavType.IntType
            })
        ) { navBackStackEntry ->
            /*val album = navBackStackEntry.arguments?.getString("album")
                ?.let { Gson().fromJson(it, Album::class.java) } ?: throw NullPointerException()
            */

            val albumId =
                navBackStackEntry.arguments?.getInt("album") ?: throw NullPointerException()
            val album = NetworkUiState.albumsResponse!!.albums.items[albumId]
            var tracks by remember { mutableStateOf<TrackResponse?>(null) }

            LaunchedEffect(Unit) {
                withContext(Dispatchers.IO) {
                    tracks = networkViewModel.musicRepository.getAlbumTracks(album.id, NetworkUiState.token)
                }
            }
            if (tracks != null) {
                AlbumScreen(
                    networkViewModel.musicRepository,
                    NetworkUiState.albumsResponse!!.albums.items[albumId],
                    tracks!!
                )
            }
            else LoadingScreen()
        }
    }
}