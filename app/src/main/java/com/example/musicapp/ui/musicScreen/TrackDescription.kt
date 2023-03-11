package com.example.musicapp.ui.musicScreen

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.musicapp.R


@Composable
fun TrackDescription(
    @DrawableRes trackImage: Int,
    @StringRes trackTitle: Int,
    @StringRes trackAuthor: Int,
    iconSize: Pair<Dp, Dp>,
    modifier: Modifier = Modifier
) {
    // Track image
    Image(
        painter = painterResource(id = trackImage),
        contentDescription = stringResource(id = R.string.music_image),
        contentScale = ContentScale.Crop,
        modifier = modifier
            .sizeIn(maxWidth = iconSize.first, maxHeight = iconSize.second)
            .aspectRatio(1f)
            .clip(RoundedCornerShape(10.dp))
    )
    // Track description
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                start = 20.dp, end = 20.dp
            )
    ) {
        // Author, title
        Column(verticalArrangement = Arrangement.spacedBy(10.dp))
        {
            Text(text = stringResource(id = trackTitle))
            Text(text = stringResource(id = trackAuthor))
        }
        Spacer(modifier = Modifier.weight(1f))
        // Sharing button
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            IconButton(onClick = { /*TODO*/ }) {
                Icon(
                    painter = painterResource(id = R.drawable.share_icon),
                    contentDescription = stringResource(id = R.string.share)
                )
            }
            IconButton(onClick = { /*TODO*/ }) {
                Icon(
                    painter = painterResource(id = R.drawable.favourite),
                    contentDescription = stringResource(id = R.string.favourite)
                )
            }
        }

    }
}