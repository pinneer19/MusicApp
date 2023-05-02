package com.example.musicapp.ui.albumScreen

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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.musicapp.model.Playlist
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MainSheetContent(
    playlist: Playlist,
    sheetState: BottomSheetScaffoldState,
    fraction: Float,
    screenHeight: Dp,
) {

    var offsetY by remember {
        mutableStateOf(0f)
    }
    val scope = rememberCoroutineScope()


    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxWidth()
            .height(screenHeight - 500.dp)
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        MaterialTheme.colors.secondaryVariant,
                        MaterialTheme.colors.secondary,
                        Color.White
                    ),
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
                    when {
                        dragAmount > 0 -> {
                            scope.launch {
                                if (sheetState.bottomSheetState.isExpanded) {
                                    sheetState.bottomSheetState.collapse()
                                }
                            }
                        }
                        dragAmount < 0 -> {
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
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()

        ) {
            ImageCard(playlist = playlist, modifier = Modifier.fillMaxWidth())

            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 15.dp, start = 20.dp, end = 20.dp),
                textAlign = TextAlign.Center,
                text = playlist.user.name,
                style = MaterialTheme.typography.body1.copy(fontSize = 14.sp, letterSpacing = 2.sp),
                color = MaterialTheme.colors.onSecondary,
                overflow = TextOverflow.Ellipsis
            )

        }
    }
}
