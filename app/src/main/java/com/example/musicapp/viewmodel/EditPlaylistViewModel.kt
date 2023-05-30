package com.example.musicapp.viewmodel

import android.net.Uri
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.musicapp.MusicApplication
import com.example.musicapp.data.playlist.Constant
import com.example.musicapp.data.playlist.PlaylistRepository
import com.example.musicapp.model.Track
import com.example.musicapp.model.UserPlaylist
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


data class EditUiState(
    val playlist: UserPlaylist? = null,
    val newPlaylistName: String = "",
    val imageUri: String? = null
)

class EditPlaylistViewModel(private val playlistRepository: PlaylistRepository) : ViewModel() {

    private val _editUiState = MutableStateFlow(EditUiState())
    val editUiState: StateFlow<EditUiState> = _editUiState.asStateFlow()


    fun updatePlaylist(documentId: String) {
        viewModelScope.launch {
            val playlist = playlistRepository.getPlaylist(documentId)
            _editUiState.update { it.copy(playlist = playlist) }
        }
    }

    fun updatePlaylistName(newName: String) {
        if (newName.length <= Constant.MAX_PLAYLIST_NAME_LENGTH && newName.isNotEmpty()) {
            _editUiState.update { it.copy(newPlaylistName = newName) }
        }
    }


    fun editPlaylist() {
        val currentState = _editUiState.value
        val playlist = currentState.playlist ?: return

        val newPlaylist = playlist.copy(
            title = currentState.newPlaylistName
        )

        playlistRepository.editPlaylist(newPlaylist)
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application =
                    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as MusicApplication)
                val playlistRepository = application.container.playlistRepository
                EditPlaylistViewModel(playlistRepository = playlistRepository)
            }
        }
    }
}