package com.example.musicapp.ui.musicScreen

import android.util.Log
import com.example.musicapp.viewmodel.MusicViewModel
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.example.musicapp.R
import com.example.musicapp.model.Track
import com.example.musicapp.viewmodel.MusicUiState


@Composable
fun MusicScreen(
    track: Track,
    musicViewModel: MusicViewModel,
    musicUiState: MusicUiState,
    onCollapseClicked: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val image = R.drawable.trackimage
    val title = track.title
    val author = track.artist.name

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
        PlayerSlider(

            musicViewModel = musicViewModel,
            currentPosition = musicUiState.currentPosition,
            onValueChanged = { position ->
                musicViewModel.seek(position.toLong())
            },
        )

        PlayerButtons(
            onPlayPauseClicked = {
                if (musicUiState.isPlaying) musicViewModel.pause()
                else musicViewModel.play()
            },
            onNextClicked = {
                musicViewModel.playNextTrack()
            },
            onPrevClicked = {
                musicViewModel.playPreviousTrack()
            },
            onRewindClicked = {
                 musicViewModel.seek(musicUiState.currentPosition - FORWARD_BACKWARD_STEP)
            },
            onForwardClicked = {
                musicViewModel.seek(musicUiState.currentPosition + FORWARD_BACKWARD_STEP)
            },
            playIcon = if (musicUiState.isPlaying) ImageVector.vectorResource(id = R.drawable.baseline_pause_24) else Icons.Filled.PlayArrow,
            modifier = Modifier.padding(bottom = 30.dp)
        )
    }
}