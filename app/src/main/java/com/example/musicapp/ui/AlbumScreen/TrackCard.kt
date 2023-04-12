package com.example.musicapp.ui.AlbumScreen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.musicapp.R
import com.example.musicapp.model.Track

@Composable
fun TrackCard(
    track: Track,
    onTrackClicked: () -> Unit,
    modifier: Modifier,
) {

    Card(
        modifier = modifier
            .clickable {
                onTrackClicked()
            }
            .padding(8.dp),
        shape = RectangleShape,
        elevation = 0.dp
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier.weight(1f)) {
                Text(
                    text = track.name,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.body1
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = track.artists.joinToString(", ") { it.name },
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.subtitle1.copy(
                        fontSize = 14.sp
                    )
                )
            }

            Icon(
                painter = painterResource(id = R.drawable.more),
                contentDescription = stringResource(id = R.string.more)
            )
        }
    }
}