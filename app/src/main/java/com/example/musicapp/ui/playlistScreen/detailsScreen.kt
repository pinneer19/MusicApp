package com.example.musicapp.ui.playlistScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.modifier.modifierLocalMapOf
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.musicapp.R
import com.example.musicapp.model.Track
import com.example.musicapp.viewmodel.MusicViewModel

@Composable
fun DetailScreen(
    musicViewModel: MusicViewModel,
    tracks: List<Track>,

) {

    val musicUiState by musicViewModel.musicUiState.collectAsState()
    Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {

        Image(
            painter = painterResource(id = R.drawable.trackimage),
            contentDescription = null,
            modifier = Modifier
                .height(250.dp)
                .fillMaxWidth()
                .padding(20.dp),
            contentScale = ContentScale.Crop
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 25.dp), horizontalArrangement = Arrangement.SpaceAround
        ) {
            IconButton(onClick = {  musicViewModel.playPreviousTrack() }) {
                Icon(
                    modifier = Modifier.size(40.dp),
                    painter = painterResource(id = R.drawable.skip_previous),
                    contentDescription = null
                )
            }
            IconButton(onClick = {
                if (musicUiState.isPlaying) {
                    musicViewModel.pause()
                } else {
                    musicViewModel.play()
                }
            }) {
                Icon(
                    modifier = Modifier.size(40.dp),
                    painter = painterResource(id = if (musicUiState.isPlaying) R.drawable.baseline_pause_24 else R.drawable.play_arrow),
                    contentDescription = null
                )
            }
            IconButton(onClick = {  musicViewModel.playNextTrack() }) {
                Icon(
                    modifier = Modifier.size(40.dp),
                    painter = painterResource(id = R.drawable.skip_next),
                    contentDescription = null
                )
            }

        }
        Divider()
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth(),

            ) {
            items(tracks) {
                DetailTrackCard(
                    track = it,
                    modifier = Modifier
                )
            }
        }
    }
}

@Composable
fun DetailTrackCard(
    track: Track,
    modifier: Modifier,
) {


    Card(
        modifier = modifier
            .padding(8.dp),
        shape = RectangleShape,
        elevation = 0.dp
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier.weight(1f)) {
                Text(
                    text = track.title,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.body1
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = track.artist.name,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.subtitle1.copy(
                        fontSize = 14.sp
                    )
                )
            }
        }
    }
}