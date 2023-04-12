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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
    screenHeight: Dp,
) {


    var offsetY by remember {
        mutableStateOf(0f)
    }
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxWidth()
            .height(screenHeight - 500.dp)
            .background(
                Brush.verticalGradient(
                    colors = listOf(MaterialTheme.colors.secondaryVariant,MaterialTheme.colors.secondary, Color.White),
                ),
                alpha = fraction
                //Color.White
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
                    //if(!(sheetState.bottomSheetState.isExpanded && y < 0) && !(sheetState.bottomSheetState.isCollapsed && y > 0))
                    offsetY += dragAmount

                }
            }
            //.padding(top = 20.dp, bottom = 20.dp)

    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()

        ) {
            ImageCard(album = album, modifier = Modifier.fillMaxWidth())

            Text(
                modifier = Modifier.fillMaxWidth().padding(top = 15.dp, start = 20.dp, end = 20.dp),
                textAlign = TextAlign.Center,
                text = album.artists.joinToString(", ") { it.name },
                style = MaterialTheme.typography.body1.copy(fontSize = 14.sp, letterSpacing = 2.sp),
                color = MaterialTheme.colors.onSecondary,
                overflow = TextOverflow.Ellipsis
            )

        }
    }
}
