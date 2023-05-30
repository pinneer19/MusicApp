package com.example.musicapp.ui.playlist

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.indication
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.ModifierInfo
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import coil.request.ImageRequest
import com.example.musicapp.R
import com.example.musicapp.model.*
import com.example.musicapp.network.NetworkUiState
import com.example.musicapp.ui.favourite.FavouriteTopBar
import com.example.musicapp.ui.navigation.NavRoutes
import com.example.musicapp.ui.playlistScreen.DropDownItem
import com.example.musicapp.viewmodel.MusicViewModel
import com.example.musicapp.viewmodel.PlaylistViewModel


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun PlaylistScreen(
    playlists: List<UserPlaylist>,
    viewmodel: PlaylistViewModel,
    navController: NavController,
    musicViewModel: MusicViewModel
) {

    Scaffold(
        topBar = {
            PlaylistTopBar(viewmodel) { playlist ->
                viewmodel.addPlaylist(playlist)
            }
        },
        modifier = Modifier.fillMaxSize()

    ) {

        LazyColumn {
            items(playlists) { playlist ->
                PlaylistItem(
                    playlist = playlist,
                    dropDownItems = listOf(
                        DropDownItem("Edit"),
                        DropDownItem("Delete")
                    ),
                    onItemClick = {
                        when (it.text) {
                            "Edit" -> navController.navigate(NavRoutes.Edit.name + "/${playlist.documentId}")
                            "Delete" -> {
                                viewmodel.deletePlaylist(playlist.documentId)
                                viewmodel.getUserPlaylists()
                            }
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    onTap = {
                        NetworkUiState.trackResponse = playlist.tracklist.map {
                            Track(
                                preview = it.preview,
                                artist = Artist(name = it.artist),
                                title = it.title,
                                album = null,
                                duration = null
                            )
                        }
                        musicViewModel.updatePlaylist(NetworkUiState.trackResponse!!)
                        navController.navigate(NavRoutes.Detail.name)

                    }
                )

            }
        }
    }
}

@Composable
fun PlaylistItem(
    playlist: UserPlaylist,
    dropDownItems: List<DropDownItem>,
    modifier: Modifier = Modifier,
    onItemClick: (DropDownItem) -> Unit,
    onTap: () -> Unit
) {

    var isContextMenuVisible by remember {
        mutableStateOf(false)
    }
    var pressOffset by remember {
        mutableStateOf(DpOffset.Zero)
    }
    var itemHeight by remember {
        mutableStateOf(0.dp)
    }
    val density = LocalDensity.current

    val interactionSource = remember {
        MutableInteractionSource()
    }

    Card(
        elevation = 4.dp,
        modifier = modifier.onSizeChanged { itemHeight = with(density) { it.height.toDp() } }) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .indication(interactionSource, LocalIndication.current)
                .pointerInput(true) {
                    detectTapGestures(
                        onLongPress = {
                            isContextMenuVisible = true
                            pressOffset = DpOffset(it.x.toDp(), it.y.toDp())
                        },
                        onPress = {
                            val press = PressInteraction.Press(it)
                            interactionSource.emit(press)
                            tryAwaitRelease()
                            interactionSource.emit(PressInteraction.Release(press))
                        },
                        onTap = {
                            onTap()
                        }
                    )
                },
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Image(
                painter = painterResource(id = R.drawable.images),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(100.dp)
                    .padding(15.dp)
            )

            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = playlist.title,
                    style = MaterialTheme.typography.body1.copy(fontSize = 22.sp)
                )
                Spacer(modifier = Modifier.height(5.dp))
                Text(
                    text = "Tracks amount: ${playlist.tracksAmount}",
                    style = MaterialTheme.typography.subtitle1.copy(fontSize = 16.sp)
                )

            }

            Icon(
                painter = painterResource(id = R.drawable.more),
                contentDescription = stringResource(id = R.string.more)
            )
        }
        DropdownMenu(
            expanded = isContextMenuVisible,
            onDismissRequest = { isContextMenuVisible = false },
            offset = pressOffset.copy(y = pressOffset.y - itemHeight)
        ) {
            dropDownItems.forEach { item ->
                DropdownMenuItem(onClick = { onItemClick(item); isContextMenuVisible = false }) {
                    Text(text = item.text)
                }
            }
        }
    }

}