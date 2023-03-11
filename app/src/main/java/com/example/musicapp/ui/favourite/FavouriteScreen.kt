package com.example.musicapp.ui.favourite

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.example.musicapp.R

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun FavouriteScreen(
    favourites: List<String>,
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            FavouriteTopBar()
        }

    ) {
        LazyColumn {
            items(favourites) { favourite ->
                Row() {
                    TODO("Track image")

                    Column() {
                        TODO("Track title")
                        TODO("Track author")
                    }
                }
            }
        }
    }
}
