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
import com.example.musicapp.ui.ProfileScreen.ProfileScreen
import com.example.musicapp.ui.albumScreen.AlbumScreen
import com.example.musicapp.ui.mainScreen.MainScreen
import com.example.musicapp.ui.navigation.NavRoutes
import com.example.musicapp.viewmodel.AuthViewModel


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeNavGraph(
    navController: NavHostController,
    networkViewModel: NetworkViewModel,
    musicViewModel: MusicViewModel,
    authViewModel: AuthViewModel,
    logOutAction: () -> Unit
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

    }
}


