package com.example.musicapp.ui.playlist

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.example.musicapp.R
import com.example.musicapp.ui.favourite.FavouriteTopBar

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun Playlist(
    playlists: List<String>
) {
    Scaffold(
        topBar = {
            PlaylistTopBar()
        },
        modifier = Modifier.fillMaxSize()

    ) {
        LazyColumn {
            items(playlists) { favourite ->
                Row() {
                    TODO("Add playlist icon")

                    Column() {
                        TODO("Playlist name")

                        TODO("Amount of tracks / playlist duration")

                    }

                    Icon(
                        painter = painterResource(id = R.drawable.more),
                        contentDescription = stringResource(id = R.string.more)
                    )
                }
            }
        }
    }
}