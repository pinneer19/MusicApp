package com.example.musicapp.ui.mainScreen

//import com.example.musicapp.model.TrackResponse
import MusicUiState
import MusicViewModel
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
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
    onClick: (Int) -> Unit,
    onNext: () -> Unit,
    modifier: Modifier = Modifier
) {
    //var sliderState by remember { mutableStateOf(0f) }
    Column(modifier = modifier.background(Color.White)) {
        LinearProgressIndicator(
            progress = 1f,
            modifier = Modifier
                .fillMaxWidth()
                .height(2.dp),
            backgroundColor = Color.LightGray,
            color = MaterialTheme.colors.secondary
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 10.dp, end = 10.dp)
                .clickable {
                    onClick(musicUiState.currentTrackIndex)
                    //navController.navigate(NavRoutes.Track.name + "/$trackIndex")
                },
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween

        ) {

            val title = track.title
            val author = track.artist.name

            val coroutineScope = rememberCoroutineScope()

            val context = LocalContext.current

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
                        Log.i("PLAYER", "BUTTON: ${musicUiState.isPlaying}")
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
        Divider(
            color = MaterialTheme.colors.onSecondary,
            thickness = 1.dp
        )
    }

}