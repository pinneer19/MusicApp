package com.example.musicapp.ui.albumScreen

import com.example.musicapp.viewmodel.MusicViewModel
import android.annotation.SuppressLint
import android.net.Uri
import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.musicapp.R
import com.example.musicapp.model.*
import com.example.musicapp.ui.musicScreen.MusicScreen
import com.example.musicapp.ui.navigation.NavRoutes
import com.google.gson.Gson
import kotlinx.coroutines.launch
import kotlin.math.roundToInt


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun AlbumScreen(
    musicViewModel: MusicViewModel,
    playlist: Playlist,
    tracks: List<Track>,

) {

    val musicUiState by musicViewModel.musicUiState.collectAsState()
    val scaffoldState = rememberScaffoldState()
    val screenHeight = LocalConfiguration.current.screenHeightDp.dp
    val bottomSheetScaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = rememberBottomSheetState(BottomSheetValue.Collapsed),
    )
    val scope = rememberCoroutineScope()
    val innerBottomSheetScaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = rememberBottomSheetState(BottomSheetValue.Collapsed),
    )
    val bottomSheetHeight = try {
        screenHeight - innerBottomSheetScaffoldState.bottomSheetState.requireOffset()
            .roundToInt().dp
    } catch (e: IllegalStateException) {
        0.dp
    }
    var fraction =
        1f - (bottomSheetHeight.value / screenHeight.value + 0.047088f) * 0.952912f
    if (fraction > 1f) fraction = 1f
    else if (fraction < 0f) fraction = 0f
    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
            //.padding(bottom = 60.dp),
        scaffoldState = scaffoldState
    ) {

        BottomSheetScaffold(
            scaffoldState = bottomSheetScaffoldState,
            modifier = Modifier.fillMaxWidth(),
            sheetContent = {
                MusicScreen(
                    track = tracks[musicUiState.currentTrackIndex],
                    musicViewModel = musicViewModel,
                    musicUiState = musicUiState,
                    onCollapseClicked = {
                        scope.launch {
                            bottomSheetScaffoldState.bottomSheetState.collapse()
                        }
                    })
            },
            sheetPeekHeight = 0.dp,
            sheetElevation = 0.dp,
            ) {
            Box(
                modifier = Modifier.fillMaxSize()
            ) {

                BottomSheetScaffold(
                    scaffoldState = innerBottomSheetScaffoldState,
                    sheetContent = {
                        SheetContent(
                            tracks = tracks,
                            currentPlayIcon = if (musicUiState.isPlaying) ImageVector.vectorResource(id = R.drawable.baseline_pause_24) else Icons.Filled.PlayArrow,
                            onPlayClicked = {
                                if (musicUiState.isPlaying) {
                                    musicViewModel.pause()
                                } else {
                                    musicViewModel.play()
                                }
                            },
                            onTrackClicked = { index ->
                                if (musicUiState.currentTrackIndex == index) {
                                    if (musicUiState.isPlaying) musicViewModel.pause()
                                    else musicViewModel.play()
                                } else musicViewModel.loadTrack(index)
                            }
                        )
                    },
                    sheetElevation = 0.dp,
                    sheetPeekHeight = 500.dp,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    MainSheetContent(
                        playlist,
                        innerBottomSheetScaffoldState,
                        fraction,
                        screenHeight
                    )
                }
                BottomTrack(
                    musicViewModel = musicViewModel,
                    musicUiState = musicUiState,
                    scaffoldState = scaffoldState,
                    track = tracks[musicUiState.currentTrackIndex],
                    onClick = {
                        scope.launch {
                            bottomSheetScaffoldState.bottomSheetState.expand()
                        }
                    },
                    onNext = {
                        musicViewModel.playNextTrack()
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.BottomCenter)
                        .background(MaterialTheme.colors.onPrimary)
                )

            }

        }
    }
}