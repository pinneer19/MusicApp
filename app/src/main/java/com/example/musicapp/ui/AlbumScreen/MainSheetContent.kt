package com.example.musicapp.ui.AlbumScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectVerticalDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material.BottomSheetScaffoldState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.musicapp.R
import com.example.musicapp.model.Album
import com.example.musicapp.model.AutoResizedText
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MainSheetContent(
    album: Album,
    sheetState: BottomSheetScaffoldState,
    scope: CoroutineScope,
    fraction: Float,
) {
    var offsetY by remember {
        mutableStateOf(0f)
    }
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxWidth()
            //.height(if (fraction >= 0.6f) (screenHeight - 490.dp) * fraction else (screenHeight - 490.dp) * 0.6f)
            .background(
                Brush.verticalGradient(
                    colors = listOf(MaterialTheme.colors.primary, Color.White),
                ),
                alpha = fraction
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
            //.padding(top = 20.dp, bottom = 20.dp)

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
