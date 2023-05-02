package com.example.musicapp.ui.navigation.graphs

import com.example.musicapp.viewmodel.MusicViewModel
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.musicapp.network.NetworkUiState
import com.example.musicapp.network.NetworkViewModel
import com.example.musicapp.ui.albumScreen.AlbumScreen
import com.example.musicapp.ui.mainScreen.MainScreen
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
                musicViewModel = musicViewModel,
                playlist = NetworkUiState.playlistsResponse!![albumId],
                tracks = NetworkUiState.trackResponse!!,
            )
        }
        /*composable(
            route = NavRoutes.Track.name + "/{state}",
            arguments = listOf(
                navArgument("track") { type = NavType.IntType },
               navArgument("state") { type = StateType() }

            )
        ) { navBackStackEntry ->
            val trackId =
                navBackStackEntry.arguments?.getInt("track") ?: throw NullPointerException()
            MusicScreen(
                track = NetworkUiState.trackResponse!![state.currentTrackIndex],
                musicViewModel = musicViewModel,
                musicUiState = state,
                onCollapseClicked = { navController.popBackStack() }
            )
        }*/
    }
}


