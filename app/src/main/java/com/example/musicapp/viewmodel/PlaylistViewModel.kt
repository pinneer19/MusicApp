package com.example.musicapp.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.*
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.musicapp.MusicApplication
import com.example.musicapp.data.music.MusicRepository
import com.example.musicapp.data.playlist.PlaylistRepository
import com.example.musicapp.model.Playlist
import com.example.musicapp.model.Track
import com.example.musicapp.model.UserPlaylist
import com.example.musicapp.model.UserTrack
import com.example.musicapp.network.NetworkUiState
import com.example.musicapp.ui.playlist.PlaylistItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException

sealed interface PlaylistUiState {
    data class Success(val playlistsResponse: List<Playlist>) : PlaylistUiState
    object Error : PlaylistUiState
    object Loading : PlaylistUiState
    companion object {
        var playlistsResponse: List<UserPlaylist>? = null
    }
}

class PlaylistViewModel(private val playlistRepository: PlaylistRepository) : ViewModel() {
    private val _isLoadingPlaylists = MutableStateFlow(false)
    val isLoadingAlbums = _isLoadingPlaylists.asStateFlow()
    init {
        getUserPlaylists()
    }

    var playlistUiState: PlaylistUiState by mutableStateOf(PlaylistUiState.Loading)
        private set

    fun addPlaylist(playlist: UserPlaylist) {
        playlistRepository.createPlaylist(playlist)
    }

    fun getUserPlaylists() {
        viewModelScope.launch {
            _isLoadingPlaylists.value = true
            playlistUiState = try {
                PlaylistUiState.playlistsResponse = playlistRepository.getPlaylists()
                Log.i("PLAYLISTS", PlaylistUiState.playlistsResponse!!.size.toString())
                _isLoadingPlaylists.emit(false)
                PlaylistUiState.Success(NetworkUiState.playlistsResponse!!)

            } catch (e: IOException) {
                _isLoadingPlaylists.emit(false)
                PlaylistUiState.Error
            } catch (e: HttpException) {
                _isLoadingPlaylists.emit(false)
                PlaylistUiState.Error
            } catch (e: Exception) {
                _isLoadingPlaylists.emit(false)
                e.printStackTrace()
                PlaylistUiState.Error
            }
        }
    }
    fun addTrack(userPlaylist: UserPlaylist, track: UserTrack) {

        val list = userPlaylist.tracklist.toMutableList()
        var trackAmount = userPlaylist.tracksAmount
        if (!list.contains(track)) {
            list.add(track)
            trackAmount++
        }
        playlistRepository.editPlaylist(userPlaylist.copy(tracklist = list, tracksAmount = trackAmount))
    }


    fun deletePlaylist(documentId: String) {
        playlistRepository.deletePlaylist(documentId)

    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application =
                    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as MusicApplication)
                val metaDataRepository = application.container.playlistRepository
                PlaylistViewModel(playlistRepository = metaDataRepository)
            }
        }
    }
}