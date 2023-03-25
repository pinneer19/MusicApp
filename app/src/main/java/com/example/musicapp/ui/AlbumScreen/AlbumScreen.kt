package com.example.musicapp.ui.AlbumScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.painterResource
import com.example.musicapp.model.Album

@Composable
fun AlbumScreen(
    album: Album
) {
    Box(contentAlignment = Alignment.Center) {
        Text(text = album.name)
    }

}