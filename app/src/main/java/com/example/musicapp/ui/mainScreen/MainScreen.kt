package com.example.musicapp.ui.mainScreen

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import com.example.musicapp.network.NetworkUiState
import com.example.musicapp.network.NetworkViewModel


//@Preview(showSystemUi = true)
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MainScreen(
    networkViewModel: NetworkViewModel,
    onNavigateClick: (Int) -> Unit,
    pullRefreshState: PullRefreshState,
    refreshing: Boolean
) {

    when (val state = networkViewModel.networkUiState) {
        is NetworkUiState.Loading -> LoadingScreen()

        is NetworkUiState.Success -> ResultScreen(
            state.albumsResponse,
            networkViewModel,
            onNavigateClick = onNavigateClick
        )

        is NetworkUiState.Error -> ErrorScreen(
            pullRefreshState,
            refreshing,
            "Check your internet connection"
        )
    }
}
