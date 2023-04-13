package com.example.musicapp.ui.mainScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.musicapp.R
import com.example.musicapp.data.DataSource
import com.example.musicapp.model.Track
import com.example.musicapp.ui.musicScreen.TrackDescription
import com.example.musicapp.ui.navigation.NavRoutes

@Composable
fun BottomTrack(
    track: Track,
    trackIndex: Int,
    onClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    var sliderState by remember { mutableStateOf(0f) }
    Column(
        modifier = modifier.padding(bottom = 60.dp),
    ) {
        LinearProgressIndicator(
            progress = 0.6f,
            modifier = Modifier
                .fillMaxWidth()
                .height(2.dp),
            backgroundColor = Color.LightGray,
            color = MaterialTheme.colors.secondary
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    onClick(trackIndex)
                    //navController.navigate(NavRoutes.Track.name + "/$trackIndex")
                },
            //.padding(bottom = 60.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween

        ) {

            val title = track.name
            val author = track.artists.joinToString(", ") { it.name }


            IconButton(onClick = { /*TODO*/ }) {
                Icon(
                    painter = painterResource(id = R.drawable.favourite),
                    contentDescription = "Like"
                )
            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = title)
                Text(
                    text = author,
                    style = MaterialTheme.typography.subtitle1.copy(fontSize = 14.sp)
                )
            }
            IconButton(onClick = { /*TODO*/ }) {
                Icon(
                    painter = painterResource(id = R.drawable.play_arrow),
                    contentDescription = stringResource(id = R.string.play_audio)
                )
            }
        }
        Divider(
            color = MaterialTheme.colors.onSecondary,
            thickness = 1.dp
        )
    }

}