package com.example.musicapp.network

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.musicapp.MusicApplication
import com.example.musicapp.data.MusicApiRepository
import com.example.musicapp.model.Playlist
import com.example.musicapp.model.Track
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

sealed interface NetworkUiState {
    data class Success(val playlistsResponse: List<Playlist>) : NetworkUiState
    object Error : NetworkUiState
    object Loading : NetworkUiState
    companion object {
        var trackResponse: List<Track>? = null
        var playlistsResponse: List<Playlist>? = null
    }
}

class NetworkViewModel(private val musicRepository: MusicApiRepository) : ViewModel() {
    /** The mutable State that stores the status of the most recent request */
    var networkUiState: NetworkUiState by mutableStateOf(NetworkUiState.Loading)
        private set

    private val _isLoadingAlbums = MutableStateFlow(false)
    val isLoadingAlbums = _isLoadingAlbums.asStateFlow()

    private val _isLoadingAlbumInfo = MutableStateFlow(false)
    val isLoadingAlbumInfo = _isLoadingAlbumInfo.asStateFlow()

    init {
        getApiAlbums()
    }

    /**
     * Gets Albums information from the Deezer API Retrofit service
     */
    fun getApiAlbums(limit: Int = 20) {
        viewModelScope.launch {
            _isLoadingAlbums.value = true
            networkUiState = try {
                NetworkUiState.playlistsResponse = musicRepository.getPlaylists(limit)
                _isLoadingAlbums.value = false
                NetworkUiState.Success(NetworkUiState.playlistsResponse!!)

            } catch (e: IOException) {
                _isLoadingAlbums.emit(false)
                NetworkUiState.Error
            } catch (e: HttpException) {
                _isLoadingAlbums.emit(false)
                NetworkUiState.Error
            }
            catch (e: Exception) {
                e.printStackTrace()
                NetworkUiState.Error
            }
        }
    }

    suspend fun getPlaylistTracks(id: String) {
        NetworkUiState.trackResponse = musicRepository.getPlaylistTracks(id)
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as MusicApplication)
                val musicApiRepository = application.container.musicApiRepository
                NetworkViewModel(musicRepository = musicApiRepository)
            }
        }
    }
}

