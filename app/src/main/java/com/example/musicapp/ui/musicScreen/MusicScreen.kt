package com.example.musicapp.ui.musicScreen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.musicapp.R
import com.example.musicapp.model.Track


@Composable
fun MusicScreen(
    track: Track,
    onCollapseClicked: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val image = R.drawable.trackimage
    val title = track.title
    val author = track.artist.name
    val duration = track.duration
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        TopAppBar(onCollapseClicked = onCollapseClicked)

        TrackDescription(
            iconSize = 300.dp to 300.dp,
            trackImage = image,
            trackAuthor = author,
            trackTitle = title,
            modifier = Modifier.weight(1f)

        )
        PlayerSlider(duration)

        PlayerButtons(modifier = Modifier.padding(bottom = 30.dp))
    }
}