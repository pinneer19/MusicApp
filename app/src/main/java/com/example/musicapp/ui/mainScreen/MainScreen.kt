package com.example.musicapp.ui.mainScreen

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import com.example.musicapp.network.NetworkUiState


//@Preview(showSystemUi = true)
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MainScreen(
    navController: NavController,
    networkUiState: NetworkUiState,
    pullRefreshState: PullRefreshState,
    refreshing: Boolean
) {

    when (networkUiState) {
        is NetworkUiState.Loading -> LoadingScreen()
        is NetworkUiState.Success -> ResultScreen(
            networkUiState.albumsResponse,
            navController,
            pullRefreshState,
            refreshing
        )
        is NetworkUiState.Error -> ErrorScreen()
    }
}
