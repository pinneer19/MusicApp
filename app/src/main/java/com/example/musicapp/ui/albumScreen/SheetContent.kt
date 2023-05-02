package com.example.musicapp.ui.albumScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import com.example.musicapp.model.Track


@Composable
fun SheetContent(
    tracks: List<Track>,
    currentPlayIcon: ImageVector,
    onPlayClicked: () -> Unit,
    onTrackClicked: (Int) -> Unit
) {
    val screenHeight = LocalConfiguration.current.screenHeightDp.dp

    Column(
        modifier = Modifier
            .heightIn(min = 500.dp, max = screenHeight)
            .fillMaxWidth()
            .padding(bottom = 52.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        OutlinedButton(
            onClick = { onPlayClicked() },
            modifier = Modifier
                .padding(bottom = 15.dp)
                .size(63.dp),
            shape = CircleShape,
            contentPadding = PaddingValues(12.dp),
            colors = ButtonDefaults.outlinedButtonColors(
                backgroundColor = Color.Black
            )
        ) {
            Icon(
                modifier = Modifier
                    .fillMaxSize(),
                imageVector = currentPlayIcon,
                contentDescription = "Play album",
                tint = Color.Green
            )
        }

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
        ) {

            itemsIndexed(
                items = tracks,
                key = { _, item -> item.id })
            { index: Int, item ->

                TrackCard(
                    track = item,
                    onTrackClicked = {
                        onTrackClicked(index)
                    },
                    modifier = Modifier.fillMaxWidth()
                )
                if (item != tracks.last()) {
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