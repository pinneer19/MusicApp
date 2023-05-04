package com.example.musicapp.ui.mainScreen

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.musicapp.R
import com.example.musicapp.model.*
import com.example.musicapp.network.NetworkViewModel
import com.example.musicapp.ui.animation.shimmerEffect
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.UnknownHostException


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ResultScreen(
    playlistsResponse: List<Playlist>,
    viewModel: NetworkViewModel,

    onNavigateClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val scaffoldState: ScaffoldState = rememberScaffoldState()
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        modifier = Modifier.fillMaxSize().padding(bottom = 56.dp),
        scaffoldState = scaffoldState
    ) {
        LazyVerticalGrid(
            verticalArrangement = Arrangement.spacedBy(15.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            contentPadding = PaddingValues(all = 10.dp),
            columns = GridCells.Fixed(2),
            modifier = modifier
                .fillMaxSize()

        ) {
            itemsIndexed(
                items = playlistsResponse,
                key = { _, item -> item.id })
            { index: Int, item ->
                PlaylistCard(
                    playlist = item,
                    onAlbumClick = {
                        val playlist = playlistsResponse[index]
                        coroutineScope.launch {
                            try {
                                withContext(Dispatchers.IO) {
                                    viewModel.getPlaylistTracks(
                                        playlist.id.toString(),
                                    )
                                }
                                onNavigateClick(index)
                            }
                            catch (ex: UnknownHostException) {

                                scaffoldState.snackbarHostState.showSnackbar(
                                    message = "Check your internet connection",
                                    actionLabel = "OK"
                                )
                            }
                        }
                    }
                )
            }
        }
    }
}


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun PlaylistCard(
    playlist: Playlist,
    onAlbumClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier
            .shadow(
                ambientColor = Color.Black,
                spotColor = Color.Black,
                elevation = 7.dp,
                shape = RoundedCornerShape(9.dp),
            )
            .shimmerEffect()
        ,
        onClick = { onAlbumClick() },
        shape = RoundedCornerShape(9.dp),
    ) {
        // 640x640 image will be casted to 500x500
        Column(
            verticalArrangement = Arrangement.spacedBy(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {

            AlbumImage(playlist.pictureBig, Modifier.fillMaxWidth().shimmerEffect())

            AutoResizedText(
                text = playlist.user.name,
                style = MaterialTheme.typography.body1,
                modifier = Modifier.padding(start = 15.dp, end = 15.dp)
            )
            Spacer(modifier = modifier.height(0.dp))
        }
    }
}

@Composable
fun AlbumImage(
    imageUrl: String,
    modifier: Modifier = Modifier
) {
    AsyncImage(
        modifier = modifier.size(width = 150.dp, height = 200.dp),
        model = ImageRequest.Builder(context = LocalContext.current)
            .data(imageUrl)
            .crossfade(true)
            .build(),
        error = painterResource(R.drawable.baseline_broken_image_24),
        contentScale = ContentScale.Crop,
        contentDescription = stringResource(R.string.album_photo)
    )
}