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


@Preview(showSystemUi = true)
@Composable
fun MusicScreen(
    modifier: Modifier = Modifier
) {
    val image = DataSource.trackImageId
    val title = DataSource.trackTitleId
    val author = DataSource.trackAuthorId
    val duration = 75000
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        TopAppBar()

        TrackDescription(
            trackImage = image,
            trackAuthor = author,
            trackTitle = title,
            modifier = Modifier.weight(1f)

        )


        PlayerSlider(duration)

        PlayerButtons(modifier = Modifier.padding(bottom = 30.dp))
    }


}


@Composable
fun TopAppBar() {
    // Some buttons such as hide page, adding to playlist and so on
    Row(
        modifier = Modifier.fillMaxWidth()
    ) {

        IconButton(onClick = { /*TODO*/ }) {
            Icon(
                imageVector = Icons.Default.KeyboardArrowDown,
                contentDescription = stringResource(id = R.string.arrow_back),
                //tint = Color.Black
            )
        }
        Spacer(modifier = Modifier.weight(1f))

        IconButton(onClick = { /*TODO*/ }) {
            Icon(
                imageVector = Icons.Default.MoreVert,
                contentDescription = stringResource(id = R.string.more),
                //tint = Color.Black
            )
        }
    }
}

@Composable
fun TrackDescription(
    @DrawableRes trackImage: Int,
    @StringRes trackTitle: Int,
    @StringRes trackAuthor: Int,
    modifier: Modifier = Modifier
) {
    // Track image
    Image(
        painter = painterResource(id = trackImage),
        contentDescription = stringResource(id = R.string.music_image),
        contentScale = ContentScale.Crop,
        modifier = modifier
            .sizeIn(maxWidth = 300.dp, maxHeight = 300.dp)
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

@Composable
fun PlayerSlider(duration: Int) {

    // duration in ms
    var sliderState by rememberSaveable { mutableStateOf(0f) }


    val parsedDuration = ParseSliderValue(duration)
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                start = 20.dp, end = 20.dp
            )
    ) {
        Slider(
            value = 0f,
            onValueChange = { sliderState = it },
            colors = SliderDefaults.colors(
                thumbColor = Color.Black,
                activeTrackColor = Color.Gray
            ),
            valueRange = 0f..100f
        )
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = ParseSliderValue(sliderState.toInt()), color = Color.Black)
            Spacer(modifier = Modifier.weight(1f))
            Text(text = parsedDuration, color = Color.Black)
        }
    }

}

@Composable
fun PlayerButtons(
    playerButtonSize: Dp = 72.dp,
    sideButtonSize: Dp = 42.dp,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        val buttonModifier = Modifier
            .size(sideButtonSize)
            .semantics { role = Role.Button }

        Image(
            painter = painterResource(id = R.drawable.skip_previous),
            contentDescription = "Skip Previous",
            contentScale = ContentScale.Fit,
            colorFilter = ColorFilter.tint(Color.Black),
            modifier = buttonModifier
        )

        Image(
            painter = painterResource(id = R.drawable.replay_10s),
            contentDescription = "Reply 10 Second",
            contentScale = ContentScale.Fit,
            colorFilter = ColorFilter.tint(Color.Black),
            modifier = buttonModifier
        )

        Image(
            painter = painterResource(id = R.drawable.play_circle),
            contentDescription = "Play",
            contentScale = ContentScale.Fit,
            colorFilter = ColorFilter.tint(Color.Black),
            modifier = Modifier
                .size(playerButtonSize)
                .semantics { role = Role.Button }
        )

        Image(
            painter = painterResource(id = R.drawable.forward_10s),
            contentDescription = "Forward 10 Seconds",
            contentScale = ContentScale.Fit,
            colorFilter = ColorFilter.tint(Color.Black),
            modifier = buttonModifier
        )

        Image(
            painter = painterResource(id = R.drawable.skip_next),
            contentDescription = "Skip Next",
            contentScale = ContentScale.Fit,
            colorFilter = ColorFilter.tint(Color.Black),
            modifier = buttonModifier
        )
    }
}

const val MillieSecondsInMinute = 60000
const val MillieSecondsInSecond = 1000

private fun ParseSliderValue(state: Int): String {

    val minutes = state / MillieSecondsInMinute
    val seconds = (state - MillieSecondsInMinute * minutes) / MillieSecondsInSecond

    return String.format("%d:%02d", minutes, seconds)
}