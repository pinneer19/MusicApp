package com.example.musicapp.ui.playlist

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.example.musicapp.R

@Composable
internal fun PlaylistTopBar() {
    Row(
        modifier = Modifier.fillMaxWidth()
    ) {

        Icon(
            painter = painterResource(id = R.drawable.back),
            contentDescription = stringResource(id = R.string.back)
        )
        Text(text = stringResource(id = R.string.favourite_list))

        Spacer(modifier = Modifier.weight(1f))

        Icon(
            painter = painterResource(id = R.drawable.search),
            contentDescription = stringResource(id = R.string.search_favourite)
        )
        Icon(
            painter = painterResource(id = R.drawable.add),
            contentDescription = stringResource(id = R.string.add_playlist)
        )
    }
}