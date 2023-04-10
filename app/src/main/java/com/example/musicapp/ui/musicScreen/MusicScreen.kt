package com.example.musicapp.ui.musicScreen

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.musicapp.R
import com.example.musicapp.data.DataSource
import com.example.musicapp.model.Track
import com.example.musicapp.model.TrackResponse


//@Preview(showSystemUi = true)
@Composable
fun MusicScreen(
    track: Track,
    modifier: Modifier = Modifier,
) {
    val image = DataSource.trackImageId
    val title = track.name
    val author = track.artists.joinToString(", ") { it.name }
    val duration = track.duration_ms
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        TopAppBar()

        TrackDescription(
            iconSize = 300.dp to 300.dp,
            trackImage = image,
            trackAuthor = author,
            trackTitle = title,
            modifier = Modifier.weight(1f)

        )
        PlayerSlider(duration)

        PlayerButtons(modifier = Modifier.padding(bottom = 30.dp))
    }
}