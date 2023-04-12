package com.example.musicapp.ui.AlbumScreen

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.animation.*
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectVerticalDragGestures
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
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.*
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.musicapp.R
import com.example.musicapp.model.Album
import com.example.musicapp.model.AutoResizedText
import com.example.musicapp.model.Track
import com.example.musicapp.model.TrackResponse
import com.example.musicapp.ui.mainScreen.BottomTrack
import com.example.musicapp.ui.musicScreen.TrackDescription
import com.example.musicapp.ui.navigation.NavRoutes
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlin.math.roundToInt


@OptIn(ExperimentalMaterialApi::class)
@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun AlbumScreen(
    album: Album,
    tracks: TrackResponse,
    navController: NavController
) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        val scope = rememberCoroutineScope()
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


        var trackChosen by remember {
            mutableStateOf<Pair<Track?, Int>>(null to 0)
        }

        BottomSheetScaffold(

            scaffoldState = bottomSheetScaffoldState,
            sheetContent = {
                SheetContent(tracks, navController) {
                    trackChosen = it
                }
            },

            sheetElevation = 0.dp,
            sheetPeekHeight = 500.dp,
            modifier = Modifier.fillMaxWidth()
        ) {
            MainSheetContent(album, bottomSheetScaffoldState, scope, fraction, screenHeight)
        }

        AnimatedVisibility(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter),
            visible = trackChosen.first != null,
            enter = slideInVertically(
                initialOffsetY = { it / 2 },
                animationSpec = tween(500)
            )
        ) {

            BottomTrack(
                track = trackChosen.first!!,
                trackIndex = trackChosen.second,
                navController = navController,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colors.onPrimary)
            )
        }

    }

}