package com.example.musicapp.ui.albumScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.musicapp.model.Track
import com.example.musicapp.model.UserPlaylist
import com.example.musicapp.model.UserTrack
import com.example.musicapp.ui.playlistScreen.DropDownItem
import com.example.musicapp.viewmodel.PlaylistViewModel


@Composable
fun SheetContent(
    tracks: List<Track>,
    currentPlayIcon: ImageVector,
    onPlayClicked: () -> Unit,
    onTrackClicked: (Int) -> Unit,
    userPlaylists: List<UserPlaylist>,
    playlistViewModel: PlaylistViewModel
) {
    val screenHeight = LocalConfiguration.current.screenHeightDp.dp
    var showDialog by remember { mutableStateOf(false) }
    var clickedTrack by remember { mutableStateOf<Track?>(null) }
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
                    dropDownItems = listOf(
                        DropDownItem("Add to playlist")
                    ),
                    onItemClick = { showDialog = true; clickedTrack = item },
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
        if (showDialog) {
            Dialog(
                onDismissRequest = { showDialog = false },
                properties = DialogProperties(usePlatformDefaultWidth = false)
            ) {

                Card(
                    elevation = 5.dp,
                    shape = RoundedCornerShape(15.dp),
                    modifier = Modifier
                        .fillMaxWidth(0.95f)
                        .shadow(
                            elevation = 5.dp,
                            ambientColor = MaterialTheme.colors.primary,
                            spotColor = MaterialTheme.colors.primary,
                            shape = RoundedCornerShape(15.dp)
                        )
                ) {

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp)
                            .heightIn(300.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        userPlaylists.forEach {
                            Text(text = it.title, modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
//                                    addTrackViewModel.addTrack(it, clickedTrack!!)
                                    val track = UserTrack(
                                        clickedTrack!!.title,
                                        clickedTrack!!.preview,
                                        clickedTrack!!.artist.name
                                    )
                                    playlistViewModel.addTrack(it, track)
                                    playlistViewModel.getUserPlaylists()
                                    showDialog = false
                                }
                                .padding(10.dp))
                        }
                    }


                }

            }
        }
    }
}
