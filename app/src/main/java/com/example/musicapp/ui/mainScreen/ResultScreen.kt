package com.example.musicapp.ui.mainScreen

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.Indication
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.PullRefreshState
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.size.Scale
import com.example.musicapp.R
import com.example.musicapp.model.*
import com.example.musicapp.network.NetworkUiState
import com.example.musicapp.network.NetworkViewModel
import com.example.musicapp.ui.navigation.NavRoutes
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.UnknownHostException


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ResultScreen(
    albumsResponse: AlbumsResponse,
    navController: NavController,
    viewModel: NetworkViewModel,
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
                items = albumsResponse.albums.items,
                key = { _, item -> item.id })
            { index: Int, item ->

                AlbumCard(
                    album = item,
                    onAlbumClick = {
                        val album = albumsResponse.albums.items[index]
                        coroutineScope.launch {
                            try {
                                withContext(Dispatchers.IO) {
                                    NetworkUiState.trackResponse = viewModel.musicRepository.getAlbumTracks(
                                        album.id,
                                        NetworkUiState.token
                                    )
                                }
                                navController.navigate(NavRoutes.Album.name + "/$index")
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

@Composable
fun AlbumCard(
    album: Album,
    onAlbumClick: (Album) -> Unit,
    modifier: Modifier = Modifier,
    backColor: Color = MaterialTheme.colors.primary
) {
    Card(
        modifier = modifier
            .clickable(
                interactionSource = createMutableInteractionSource(),
                indication = createIndication(),
                onClick = { onAlbumClick(album) }
            )
            .shadow(
                ambientColor = Color.Black,
                spotColor = Color.Black,
                elevation = 7.dp,
                shape = RoundedCornerShape(9.dp),
            ),
        //elevation = 15.dp,
        shape = RoundedCornerShape(9.dp),
    ) {
        // 640x640 image will be casted to 500x500
        Column(
            verticalArrangement = Arrangement.spacedBy(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {

            AlbumImage(album.images[0], Modifier.fillMaxSize())

            AutoResizedText(
                text = album.name,
                style = MaterialTheme.typography.body1,
                modifier = Modifier.padding(start = 15.dp, end = 15.dp)
            )
            Spacer(modifier = modifier.height(0.dp))
        }
    }
}

@Composable
fun AlbumImage(
    image: Image,
    modifier: Modifier = Modifier
) {
    AsyncImage(
        modifier = modifier,
        model = ImageRequest.Builder(context = LocalContext.current)
            .data(image.url)
            .crossfade(true)
            .scale(Scale.FILL)
            .build(),
        error = painterResource(R.drawable.baseline_broken_image_24),
        placeholder = painterResource(R.drawable.loading_img),
        contentDescription = stringResource(R.string.album_photo)
    )
}

@Composable
private fun createIndication(bounded: Boolean = true, color: Color = Color.Green): Indication {
    return rememberRipple(bounded = bounded, color = color)
}

@Composable
private fun createMutableInteractionSource(): MutableInteractionSource = remember {
    MutableInteractionSource()
}