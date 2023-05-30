package com.example.musicapp.ui.albumScreen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.musicapp.R
import com.example.musicapp.model.Track
import com.example.musicapp.ui.playlistScreen.DropDownItem

@Composable
fun TrackCard(
    track: Track,
    onTrackClicked: () -> Unit,
    dropDownItems: List<DropDownItem>,
    onItemClick: (DropDownItem) -> Unit,
    modifier: Modifier,
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
                    text = track.title,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.body1
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = track.artist.name,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.subtitle1.copy(
                        fontSize = 14.sp
                    )
                )
            }

            IconButton(
                onClick = { isContextMenuVisible = true }
            ) {

                Icon(
                    painter = painterResource(id = R.drawable.more),
                    contentDescription = stringResource(id = R.string.more)
                )
            }
        }
        DropdownMenu(
            expanded = isContextMenuVisible,
            onDismissRequest = { isContextMenuVisible = false },

        ) {
            dropDownItems.forEach {
                DropdownMenuItem(onClick = {
                    onItemClick(it)
                    isContextMenuVisible = false
                }) {
                    Text(text = it.text, style = MaterialTheme.typography.body1)
                }
            }
        }
    }
}