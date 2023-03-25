package com.example.musicapp.ui.mainScreen

import android.widget.Toast
import androidx.compose.foundation.Indication
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.size.Scale
import com.example.musicapp.R
import com.example.musicapp.model.Album
import com.example.musicapp.model.AlbumsResponse
import com.example.musicapp.model.Image
import com.example.musicapp.ui.navigation.NavRoutes


@Composable
fun ResultScreen(
    albumsResponse: AlbumsResponse,
    navController: NavController,
    modifier: Modifier = Modifier,
) {
    /*
var selected by remember {
    mutableStateOf(false)
}*/
    LazyVerticalGrid(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(top = 10.dp, start = 10.dp, end = 10.dp, bottom = 60.dp),
        columns = GridCells.Fixed(2),
        modifier = modifier.fillMaxSize(),

        ) {
        items(items = albumsResponse.albums.items, key = { album -> album.id }) { item ->
            AlbumCard(album = item, onAlbumClick = { navController.navigate(NavRoutes.Album.name + "/$it") })
        }
    }
}

@Composable
fun AlbumCard(
    album: Album,
    onAlbumClick: (Album) -> Unit,
    modifier: Modifier = Modifier,

    ) {
    Card(
        modifier = modifier
            .padding(4.dp)
            .clickable(
                interactionSource = CreateMutableInteractuionSource(),
                indication = CreateIndication(),
                onClick = { onAlbumClick(album) }
            ),
        elevation = 4.dp,
        shape = RoundedCornerShape(8.dp),
    ) {
        // 640x640 image will be casted to 500x500
        Column(
            verticalArrangement = Arrangement.spacedBy(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            AlbumImage(album.images[0])
            Text(
                text = album.name,
                style = MaterialTheme.typography.body1
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
            .size(500, 500)
            .scale(Scale.FILL)
            .build(),
        error = painterResource(R.drawable.baseline_broken_image_24),
        placeholder = painterResource(R.drawable.loading_img),
        contentDescription = stringResource(R.string.album_photo),
    )
}

@Composable
private fun CreateIndication(bounded: Boolean = true, color: Color = Color.Green): Indication {
    return rememberRipple(bounded = bounded, color = color)
}

@Composable
private fun CreateMutableInteractuionSource(): MutableInteractionSource = remember {
    MutableInteractionSource()
}