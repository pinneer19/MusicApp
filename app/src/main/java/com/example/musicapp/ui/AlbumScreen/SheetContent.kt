package com.example.musicapp.ui.AlbumScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.musicapp.model.Track
import com.example.musicapp.model.TrackResponse
import com.example.musicapp.ui.navigation.NavRoutes

@Composable
fun SheetContent(
    tracks: TrackResponse,
    onTrackClicked: (Pair<Track, Int>) -> Unit,
) {
    val screenHeight = LocalConfiguration.current.screenHeightDp.dp

    Column(
        modifier = Modifier
            .heightIn(min = 500.dp, max = screenHeight)
            .fillMaxWidth()
            .padding(bottom = 60.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {


        OutlinedButton(
            onClick = { /*TODO*/ },
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
                imageVector = Icons.Filled.PlayArrow,
                contentDescription = "Play album",
                tint = Color.Green
            )
        }



        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                //.clip(RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp))
        ) {


            itemsIndexed(
                items = tracks.items,
                key = { _, item -> item.href })
            { index: Int, item ->

                TrackCard(
                    track = item,
                    onTrackClicked = {
                        onTrackClicked(item to index)
                    },
                    modifier = Modifier.fillMaxWidth()
                )
                if (item != tracks.items.last()) {
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