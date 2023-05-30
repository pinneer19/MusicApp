package com.example.musicapp.ui.navigation.graphs

import android.util.Log
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.musicapp.network.NetworkUiState
import com.example.musicapp.network.NetworkViewModel
import com.example.musicapp.ui.ProfileScreen.ProfileScreen
import com.example.musicapp.ui.albumScreen.AlbumScreen
import com.example.musicapp.ui.mainScreen.ErrorScreen
import com.example.musicapp.ui.mainScreen.LoadingScreen
import com.example.musicapp.ui.mainScreen.MainScreen
import com.example.musicapp.ui.mainScreen.ResultScreen
import com.example.musicapp.ui.navigation.NavRoutes
import com.example.musicapp.ui.playlist.PlaylistScreen
import com.example.musicapp.ui.playlistScreen.DetailScreen
import com.example.musicapp.ui.playlistScreen.EditPlaylistScreen
import com.example.musicapp.viewmodel.*
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeNavGraph(
    navController: NavHostController,
    networkViewModel: NetworkViewModel,
    musicViewModel: MusicViewModel,
    authViewModel: AuthViewModel,
    logOutAction: () -> Unit,
    playlistViewModel: PlaylistViewModel
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
            //val viewModel: SignOutViewModel = viewModel(factory = SignOutViewModel.Factory)
            ProfileScreen(viewModel = authViewModel) {
                logOutAction()
            }


        }
        composable(route = NavRoutes.Playlists.name) {



            val isLoading by playlistViewModel.isLoadingAlbums.collectAsState()
            val swipeRefreshState = rememberPullRefreshState(
                refreshing = isLoading,
                onRefresh = { playlistViewModel.getUserPlaylists() }
            )

            when (val state = playlistViewModel.playlistUiState) {
                is PlaylistUiState.Loading -> LoadingScreen()

                is PlaylistUiState.Success -> PlaylistScreen(
                    PlaylistUiState.playlistsResponse!!,
                    playlistViewModel,
                    navController,
                    musicViewModel
                )

                is PlaylistUiState.Error -> ErrorScreen(
                    pullRefreshState = swipeRefreshState,
                    refreshing = isLoading,
                    "Check your internet connection"
                )
            }
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
            Log.i("LOAD", "OK")
            musicViewModel.updatePlaylist(NetworkUiState.trackResponse!!)
            Log.i("LOAD", "OK")

            Log.i("LOAD", "OK")
            AlbumScreen(
                musicViewModel = musicViewModel,
                playlist = NetworkUiState.playlistsResponse!![albumId],
                tracks = NetworkUiState.trackResponse!!,
                userPlaylists = PlaylistUiState.playlistsResponse!!,
                playlistViewModel = playlistViewModel
            )
        }

        composable(
            route = NavRoutes.Edit.name + "/{playlist}",
            arguments = listOf(navArgument("playlist") {
                type = NavType.StringType
            })
        ) { navBackStackEntry ->
            val documentId =
                navBackStackEntry.arguments?.getString("playlist") ?: throw NullPointerException()

            val editPlaylistViewModel: EditPlaylistViewModel =
                viewModel(factory = EditPlaylistViewModel.Factory)
            editPlaylistViewModel.updatePlaylist(documentId)

            EditPlaylistScreen(viewModel = editPlaylistViewModel, onCancelClicked = {
                navController.popBackStack()
            }) {
                editPlaylistViewModel.editPlaylist()
                navController.popBackStack()
            }
        }

        composable(route = NavRoutes.Detail.name) {
            DetailScreen(musicViewModel = musicViewModel, tracks = NetworkUiState.trackResponse!!)
        }

    }
}


