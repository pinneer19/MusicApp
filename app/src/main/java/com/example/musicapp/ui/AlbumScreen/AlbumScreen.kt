package com.example.musicapp.ui.AlbumScreen

import MusicViewModel
import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.animation.*
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectVerticalDragGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.outlined.PlayArrow
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.musicapp.R
import com.example.musicapp.model.*
import com.example.musicapp.network.NetworkViewModel
import com.example.musicapp.ui.mainScreen.BottomTrack
import com.example.musicapp.ui.musicScreen.TrackDescription
import com.example.musicapp.ui.navigation.NavRoutes
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlin.math.roundToInt


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun AlbumScreen(
    musicViewModel: MusicViewModel,
    playlist: Playlist,
    tracks: List<Track>,
    onClick: (Int) -> Unit
) {


    val musicUiState by musicViewModel.musicUiState.collectAsState()
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 60.dp),
        scaffoldState = scaffoldState
    ) {

        Box(
            modifier = Modifier.fillMaxSize()
        ) {

            val bottomSheetScaffoldState = rememberBottomSheetScaffoldState(
                bottomSheetState = rememberBottomSheetState(BottomSheetValue.Collapsed),
            )
            val screenHeight = LocalConfiguration.current.screenHeightDp.dp


            val bottomSheetHeight = try {
                screenHeight - bottomSheetScaffoldState.bottomSheetState.requireOffset().roundToInt().dp
            } catch (e: IllegalStateException) {
                0.dp
            }
            var fraction = 1f - (bottomSheetHeight.value / screenHeight.value + 0.047088f) * 0.952912f
            if (fraction > 1f) fraction = 1f
            else if (fraction < 0f) fraction = 0f

            BottomSheetScaffold(
                scaffoldState = bottomSheetScaffoldState,
                sheetContent = {
                    SheetContent(
                        tracks,
                        currentPlayIcon = if(musicUiState.isPlaying) ImageVector.vectorResource(id = R.drawable.baseline_pause_24) else Icons.Filled.PlayArrow,
                        onPlayClicked = {
                            if(musicUiState.isPlaying) {
                                musicViewModel.pause()
                            }
                            else {
                                musicViewModel.play()
                            }
                        },
                        onTrackClicked = { index ->
                            musicViewModel.loadTrack(index)
                        }
                    )
                },
                sheetElevation = 0.dp,
                sheetPeekHeight = 440.dp,
                modifier = Modifier.fillMaxWidth()
            ) {
                MainSheetContent(
                    playlist,
                    bottomSheetScaffoldState,
                    fraction,
                    screenHeight
                )
            }


            BottomTrack(
                musicViewModel = musicViewModel,
                musicUiState = musicUiState,
                scaffoldState = scaffoldState,
                track = tracks[musicUiState.currentTrackIndex],
                //trackIndex = musicUiState.currentTrackIndex,
                onClick = onClick,
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