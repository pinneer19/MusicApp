package com.example.musicapp.ui.mainScreen

import androidx.compose.foundation.Indication
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
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

    LazyVerticalGrid(
        verticalArrangement = Arrangement.spacedBy(10.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        contentPadding = PaddingValues(top = 10.dp, start = 10.dp, end = 10.dp, bottom = 60.dp),
        columns = GridCells.Fixed(2),
        modifier = modifier.fillMaxSize(),

        ) {
        itemsIndexed(
            items = albumsResponse.albums.items,
            key = { _, item -> item.id }) { index: Int, item ->
            AlbumCard(
                album = item,
                onAlbumClick = { navController.navigate(NavRoutes.Album.name + "/$index") })
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
            .drawBehind {
                drawRoundRect(
                    color = backColor,
                    cornerRadius = CornerRadius(32f,32f)
                )
            }
            .clickable(
                interactionSource = createMutableInteractionSource(),
                indication = createIndication(),
                onClick = { onAlbumClick(album) }
            )
            .padding(bottom = 2.dp)
        ,
        elevation = 8.dp,
        shape = RoundedCornerShape(9.dp),
    ) {
        // 640x640 image will be casted to 500x500
        Column(
            verticalArrangement = Arrangement.spacedBy(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            //Spacer(modifier = modifier.height(0.dp))
            AlbumImage(album.images[0], Modifier.fillMaxWidth())
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
            .build(),
        error = painterResource(R.drawable.baseline_broken_image_24),
        placeholder = painterResource(R.drawable.loading_img),
        contentDescription = stringResource(R.string.album_photo),
        contentScale = ContentScale.FillBounds
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