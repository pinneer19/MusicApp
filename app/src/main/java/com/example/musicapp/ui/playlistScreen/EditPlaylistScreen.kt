package com.example.musicapp.ui.playlistScreen

import android.content.ContentUris
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.musicapp.data.playlist.Constant.MAX_PLAYLIST_NAME_LENGTH
import com.example.musicapp.viewmodel.EditPlaylistViewModel

@Composable
fun EditPlaylistScreen(
    viewModel: EditPlaylistViewModel,
    onCancelClicked: () -> Unit,
    onSaveClicked: () -> Unit
) {

    val state by viewModel.editUiState.collectAsState(initial = null)


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {


        OutlinedTextField(
            value = state?.newPlaylistName ?: "",
            onValueChange = {
                viewModel.updatePlaylistName(it)
            },
            label = { Text("New playlist name") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 15.dp),
            singleLine = true
        )

        Row(modifier = Modifier.fillMaxWidth().padding(top = 15.dp), horizontalArrangement = Arrangement.SpaceAround) {

            Button(onClick = onCancelClicked) {
                Text(text = "Cancel")
            }

            Button(onClick = onSaveClicked) {
                Text(text = "Save")
            }
        }


    }


}

