package com.example.musicapp.ui.albumScreen

import com.example.musicapp.viewmodel.MusicUiState
import com.example.musicapp.viewmodel.MusicViewModel
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.musicapp.R
import com.example.musicapp.model.Track
import kotlinx.coroutines.launch
import java.net.UnknownHostException

@Composable
fun BottomTrack(
    musicViewModel: MusicViewModel,
    musicUiState: MusicUiState,
    scaffoldState: ScaffoldState,
    track: Track,
    onClick: () -> Unit,
    onNext: () -> Unit,
    modifier: Modifier = Modifier
) {


    Column(modifier = modifier.background(Color.White)) {
        LinearProgressIndicator(
            progress = musicUiState.currentPosition / 30_000f,
            modifier = Modifier
                .fillMaxWidth()
                .height(3.5f.dp),
            backgroundColor = Color.LightGray,
            color = MaterialTheme.colors.secondary
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    onClick()
                }
                .padding(start = 10.dp, end = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween

        ) {

            val title = track.title
            val author = track.artist.name

            val coroutineScope = rememberCoroutineScope()
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.body1,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = author,
                    style = MaterialTheme.typography.subtitle1.copy(fontSize = 14.sp),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }

            IconButton(
                onClick = {
                coroutineScope.launch {
                    try {

                        if (musicUiState.isPlaying) {
                            musicViewModel.pause()
                        }
                        else {
                            musicViewModel.play()
                            /*musicViewModel.loadTrack(musicUiState.currentTrackIndex, musicUiState.currentPosition)
                            musicViewModel.updatePlayingStatus(true)*/
                        }


                    }
                    catch (ex: UnknownHostException) {
                        scaffoldState.snackbarHostState.showSnackbar(
                            message = "Check your internet connection",
                            actionLabel = "OK"
                        )
                    }
                }
            }) {
                Icon(
                    painter = if(musicUiState.isPlaying) painterResource(id = R.drawable.baseline_pause_24) else painterResource(id = R.drawable.play_arrow),
                    contentDescription = stringResource(id = R.string.play_audio)
                )
            }

            IconButton(
                onClick = {
                    onNext()
                },
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.skip_next),
                    contentDescription = "Like",
                    tint = Color.Black
                )
            }
        }
        Spacer(modifier = Modifier.height(1.dp))
    }

}