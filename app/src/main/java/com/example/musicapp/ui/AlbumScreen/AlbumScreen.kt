package com.example.musicapp.ui.AlbumScreen

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.animation.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectVerticalDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
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
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.musicapp.R
import com.example.musicapp.data.MusicRepository
import com.example.musicapp.model.Album
import com.example.musicapp.model.AutoResizedText
import com.example.musicapp.model.Track
import com.example.musicapp.model.TrackResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlin.math.roundToInt


@OptIn(ExperimentalMaterialApi::class)
@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun AlbumScreen(
    musicRepository: MusicRepository,
    album: Album,
    tracks: TrackResponse,
) {
    val scope = rememberCoroutineScope()

    val bottomSheetScaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = rememberBottomSheetState(BottomSheetValue.Collapsed)
    )
    val screenHeight = LocalConfiguration.current.screenHeightDp.dp

    val bottomSheetHeight = try {
        screenHeight - bottomSheetScaffoldState.bottomSheetState.requireOffset().roundToInt().dp
    } catch (e: IllegalStateException) {
        0.dp
    }

    Log.i("FRACT", "${bottomSheetHeight.value / screenHeight.value}")
    var fraction = 1f - (bottomSheetHeight.value / screenHeight.value + 0.053f) * 0.947f
    //Log.i("FRACT", "$fraction")
    if (fraction > 1f) fraction = 1f
    else if (fraction < 0f) fraction = 0f
    BottomSheetScaffold(
        scaffoldState = bottomSheetScaffoldState,
        sheetContent = {
            SheetContent(tracks, bottomSheetScaffoldState.bottomSheetState, fraction)
        },
        sheetElevation = 0.dp,
        /*sheetShape = RoundedCornerShape(
            bottomStart = 0.dp,
            bottomEnd = 0.dp,
            topStart = radius,
            topEnd = radius
        ),*/
        sheetPeekHeight = 500.dp,
        modifier = Modifier.fillMaxSize()


    ) {

        MainSheetContent(album, bottomSheetScaffoldState, scope, fraction)

    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MainSheetContent(
    album: Album,
    sheetState: BottomSheetScaffoldState,
    scope: CoroutineScope,
    fraction: Float,

    ) {


    val screenHeight = LocalConfiguration.current.screenHeightDp.dp

    var offsetY by remember {
        mutableStateOf(0f)
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxWidth()
            .height(screenHeight - 490.dp)
            .background(
                color = MaterialTheme.colors.secondary.copy(fraction)
            )
            .graphicsLayer(
                alpha = fraction,
                scaleX = fraction,
                scaleY = fraction
            )
            .pointerInput(Unit) {
                detectVerticalDragGestures { change, dragAmount ->
                    change.consume()
                    val y = dragAmount
                    when {
                        y > 0 -> {
                            scope.launch {
                                if (sheetState.bottomSheetState.isExpanded) {
                                    sheetState.bottomSheetState.collapse()
                                }
                            }
                        }
                        y < 0 -> {
                            scope.launch {
                                if (sheetState.bottomSheetState.isCollapsed) {
                                    sheetState.bottomSheetState.expand()
                                }
                            }
                        }
                    }
                    offsetY += dragAmount

                }
            }

    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(10.dp),
            //modifier = Modifier.fillMaxWidth()

            ) {
            AsyncImage(
                model = ImageRequest.Builder(context = LocalContext.current)
                    .data(album.images[0].url)
                    .build(),
                contentDescription = stringResource(R.string.album_photo),
                contentScale = ContentScale.FillBounds
            )
            AutoResizedText(
                text = album.name,
                modifier = Modifier.padding(start = 20.dp, end = 20.dp),
                style = MaterialTheme.typography.h2,
                color = MaterialTheme.colors.onSecondary,
                isWidthOverflow = true
            )
            Text(
                text = album.artists.joinToString(", ") { it.name },
                style = MaterialTheme.typography.body1,
                color = MaterialTheme.colors.onSecondary
            )

        }
    }
}

@Composable
fun TrackCard(
    track: Track,
    modifier: Modifier
) {
    Card(
        modifier = modifier.padding(8.dp),
        shape = RectangleShape,
        elevation = 0.dp
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Column() {
                Text(text = track.name)
                Spacer(modifier = Modifier.height(10.dp))
                Text(text = track.artists.joinToString(", ") { it.name })
            }
            Spacer(modifier = Modifier.weight(1f))
            Icon(
                painter = painterResource(id = R.drawable.more),
                contentDescription = stringResource(id = R.string.more)
            )
        }
    }
}


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SheetContent(
    tracks: TrackResponse,
    sheetState: BottomSheetState,
    fraction: Float
) {
    val screenHeight = LocalConfiguration.current.screenHeightDp.dp

    Column(
        modifier = Modifier
            .heightIn(min = 500.dp, max = screenHeight)
            .fillMaxWidth()
            .padding(bottom = 60.dp)
    ) {
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(15.dp)
                .background(MaterialTheme.colors.secondary.copy(fraction))
        )
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxWidth()
                //.padding(bottom = 15.dp)
                .background(MaterialTheme.colors.secondary.copy(fraction))
        ) {

            OutlinedButton(
                onClick = { /*TODO*/ },
                modifier = Modifier
                    .size(72.dp),
                //.alpha(1f - fraction),
                shape = CircleShape,
                contentPadding = PaddingValues(12.dp),
                elevation = ButtonDefaults.elevation(4.dp),
                colors = ButtonDefaults.outlinedButtonColors(
                    backgroundColor = Color.Black
                ),
                border = BorderStroke(1.dp, Color.White),
            ) {
                Icon(
                    modifier = Modifier
                        .fillMaxSize(),
                    imageVector = Icons.Filled.PlayArrow,
                    contentDescription = "Play album",
                    tint = Color.Green
                )

                // If the song is loaded, pause the actual song
                /* if (songLoaded) {
             pauseTheSong(context = context)
         }*/
            }

        }
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(10.dp)
                .background(MaterialTheme.colors.secondary.copy(fraction))
        )
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp))
        ) {
            items(tracks.items) {
                TrackCard(it, Modifier.fillMaxWidth())
                if (it != tracks.items.last()) {
                    Spacer(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(1.dp)
                            .background(MaterialTheme.colors.primary)
                    )
                }
            }
        }
    }
}