package com.example.musicapp.ui.navigation.graphs

import MusicViewModel
import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.example.musicapp.data.BottomNavItems
import com.example.musicapp.data.MusicRepositoryManager
import com.example.musicapp.network.NetworkUiState
import com.example.musicapp.network.NetworkViewModel
import com.example.musicapp.ui.AlbumScreen.AlbumScreen
import com.example.musicapp.ui.mainScreen.HomeScreen
import com.example.musicapp.ui.mainScreen.MainScreen
import com.example.musicapp.ui.musicScreen.MusicScreen
import com.example.musicapp.ui.navigation.NavRoutes


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeNavGraph(
    navController: NavHostController,
    networkViewModel: NetworkViewModel,
    musicViewModel: MusicViewModel
) {
    NavHost(
        navController = navController,
        route = Graph.HOME,
        startDestination = NavRoutes.Start.name
    ) {
        composable(route = NavRoutes.Start.name) {
            val isLoading by networkViewModel.isLoadingAlbums.collectAsState()
            val swipeRefreshState = rememberPullRefreshState(
                refreshing = isLoading,
                onRefresh = { networkViewModel.getApiAlbums() }
            )

            MainScreen(
                networkViewModel = networkViewModel,
                pullRefreshState = swipeRefreshState,
                refreshing = isLoading,
                onNavigateClick = { index ->
                    navController.navigate(NavRoutes.Album.name + "/$index")
                }
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

            musicViewModel.updatePlaylist(NetworkUiState.trackResponse!!)
            AlbumScreen(
                musicViewModel,
                NetworkUiState.playlistsResponse!![albumId],
                NetworkUiState.trackResponse!!,
                onClick = { trackIndex ->
                    navController.navigate(NavRoutes.Track.name + "/$trackIndex")
                }
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

            MusicScreen(
                //track = NetworkUiState.trackResponse!!.items[trackId],
                track = NetworkUiState.trackResponse!![trackId],
                onCollapseClicked = { navController.popBackStack() }
            )
        }
    }
}


