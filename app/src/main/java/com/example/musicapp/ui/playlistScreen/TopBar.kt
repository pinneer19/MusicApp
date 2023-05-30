package com.example.musicapp.ui.playlist

import android.widget.Toast
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavController
import com.example.musicapp.R
import com.example.musicapp.data.playlist.Constant.MAX_PLAYLIST_NAME_LENGTH
import com.example.musicapp.model.UserPlaylist
import com.example.musicapp.ui.navigation.NavRoutes
import com.example.musicapp.viewmodel.PlaylistViewModel

@Composable
fun PlaylistTopBar(
    viewmodel: PlaylistViewModel,
    onCreatePlaylist: (UserPlaylist) -> Unit,
) {
    var showDialog by remember { mutableStateOf(false) }
    var playlistName by remember { mutableStateOf("") }


    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 10.dp, end = 10.dp, bottom = 3.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = R.drawable.back),
                contentDescription = stringResource(id = R.string.back),
                modifier = Modifier.size(28.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = stringResource(id = R.string.playlists)
            )
            Spacer(modifier = Modifier.weight(1f))
            IconButton(onClick = { showDialog = true }) {
                Icon(
                    painter = painterResource(id = R.drawable.add),
                    contentDescription = stringResource(id = R.string.add_playlist),
                    modifier = Modifier.size(28.dp)
                )
            }
        }
        Divider(
            color = MaterialTheme.colors.primary,
            thickness = 2.dp,
            modifier = Modifier.fillMaxWidth()
        )
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
//                    .border(1.dp, color = Color.Yellow, shape = RoundedCornerShape(15.dp))
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
                        .padding(15.dp),
                    verticalArrangement = Arrangement.spacedBy(25.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    TextField(
                        value = playlistName,
                        onValueChange = {
                            if (playlistName.length <= MAX_PLAYLIST_NAME_LENGTH) playlistName = it
                        },
                        label = { Text(text = stringResource(id = R.string.enter_playlist_name)) },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        Button(
                            onClick = { showDialog = false; playlistName = "" }
                        ) {
                            Text(text = "Cancel")
                        }
                        Button(
                            onClick = {
                                if (playlistName.isNotEmpty()) {
                                    onCreatePlaylist(
                                        UserPlaylist().copy(title = playlistName)
                                    )

                                    playlistName = ""
                                    showDialog = false
                                    viewmodel.getUserPlaylists()
                                }
                            }
                        ) {
                            Text(text = "Create")
                        }


                    }
                }
            }

        }
    }
}