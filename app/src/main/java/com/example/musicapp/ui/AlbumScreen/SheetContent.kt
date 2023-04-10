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
import com.example.musicapp.model.TrackResponse
import com.example.musicapp.ui.navigation.NavRoutes

@Composable
fun SheetContent(
    tracks: TrackResponse,
    navController: NavController,
    onTrackClicked: (Boolean) -> Unit,
) {
    val screenHeight = LocalConfiguration.current.screenHeightDp.dp

    Column(
        modifier = Modifier
            .heightIn(min = 500.dp, max = screenHeight)
            .fillMaxWidth()
            .padding(bottom = 60.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Surface(
            color = Color.Transparent
        ) {

            OutlinedButton(
                onClick = { /*TODO*/ },
                modifier = Modifier
                    .padding(top = 15.dp, bottom = 15.dp)
                    .size(72.dp),
                shape = CircleShape,
                contentPadding = PaddingValues(12.dp),
                elevation = ButtonDefaults.elevation(4.dp),
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

                // If the song is loaded, pause the actual song
                /* if (songLoaded) {
                     pauseTheSong(context = context)
                 }*/
            }

        }

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp))
        ) {

            /*
            * itemsIndexed(
                items = albumsResponse.albums.items,
                key = { _, item -> item.id })
            { index: Int, item ->
                AlbumCard(
                    album = item,
                    onAlbumClick = { navController.navigate(NavRoutes.Album.name + "/$index") })
            }
            * */

            itemsIndexed(
                items = tracks.items,
                key = { _, item -> item.href })
            { index: Int, item ->
                /*AlbumCard(
                    album = item,
                    onAlbumClick = { navController.navigate(NavRoutes.Album.name + "/$index") })*/
                TrackCard(
                    track = item,
                    onTrackClicked =  {
                        onTrackClicked(true)
                        ///BottomTrack()
                        navController.navigate(NavRoutes.Track.name + "/$index")
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