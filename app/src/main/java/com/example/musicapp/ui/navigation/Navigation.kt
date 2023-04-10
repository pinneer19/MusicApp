package com.example.musicapp.ui.navigation

//import com.example.musical.ui.loginScreen.LoginScreen
import android.util.Log
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.rememberPullRefreshState
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
import com.example.musicapp.ui.mainScreen.ErrorScreen
import com.example.musicapp.ui.mainScreen.LoadingScreen
import com.example.musicapp.ui.mainScreen.MainScreen
import com.example.musicapp.ui.musicScreen.MusicScreen
import com.example.musicapp.ui.theme.MusicAppTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.UnknownHostException


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun Navigation(
    navController: NavHostController,
    networkViewModel: NetworkViewModel,
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
            val isLoading by networkViewModel.isLoadingAlbums.collectAsState()
            val swipeRefreshState = rememberPullRefreshState(
                refreshing = isLoading,
                onRefresh = { networkViewModel.getApiAlbums() })

            MainScreen(
                navController = navController,
                networkViewModel = networkViewModel,
                pullRefreshState = swipeRefreshState,
                refreshing = isLoading
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

           val albumId =
                navBackStackEntry.arguments?.getInt("album") ?: throw NullPointerException()

                AlbumScreen(
                    NetworkUiState.albumsResponse!!.albums.items[albumId],
                    NetworkUiState.trackResponse!!,
                    navController
                )
        }
        composable(
            route = NavRoutes.Track.name + "/{track}",
            arguments = listOf(navArgument("track") {
                type = NavType.IntType
            })
        ) { navBackStackEntry ->
            val trackId =
                navBackStackEntry.arguments?.getInt("track") ?: throw NullPointerException()

            MusicScreen(NetworkUiState.trackResponse!!.items[trackId])

        }
    }
}