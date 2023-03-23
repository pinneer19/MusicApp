package com.example.musicapp.ui.mainScreen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.musicapp.network.NetworkUiState


//@Preview(showSystemUi = true)
@Composable
fun MainScreen(
    networkUiState: NetworkUiState
) {
    when(networkUiState) {
        is NetworkUiState.Loading -> LoadingScreen()
        is NetworkUiState.Success -> ResultScreen()
        is NetworkUiState.Error -> ErrorScreen()
    }
}
