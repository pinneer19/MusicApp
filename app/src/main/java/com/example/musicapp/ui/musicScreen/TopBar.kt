package com.example.musicapp.ui.musicScreen

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.example.musicapp.R


@Composable
fun TopAppBar(
    onCollapseClicked: () -> Unit
) {
    // Some buttons such as hide page, adding to playlist and so on
    Row(
        modifier = Modifier.fillMaxWidth()
    ) {

        IconButton(onClick = { onCollapseClicked() }) {
            Icon(
                painter = painterResource(id = R.drawable.arrow_down),
                contentDescription = stringResource(id = R.string.arrow_back),
                //tint = Color.Black
            )
        }
        Spacer(modifier = Modifier.weight(1f))

        IconButton(onClick = { /*TODO*/ }) {
            Icon(
                painter = painterResource(id = R.drawable.more),
                contentDescription = stringResource(id = R.string.more),
                //tint = Color.Black
            )
        }
    }
}
