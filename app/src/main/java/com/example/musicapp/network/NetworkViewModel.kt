package com.example.musicapp.network

import android.util.Log
import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.musicapp.MusicApplication
import com.example.musicapp.data.MusicRepository
import com.example.musicapp.data.NetworkMusicRepository
import com.example.musicapp.model.AlbumsResponse
import com.example.musicapp.model.TokenResponse
import com.example.musicapp.model.TrackResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.*
import java.io.IOException
import java.net.UnknownHostException

sealed interface NetworkUiState {
    data class Success(val albumsResponse: AlbumsResponse) : NetworkUiState
    object Error : NetworkUiState
    object Loading : NetworkUiState
    companion object {
        var token: String = ""
        var albumsResponse: AlbumsResponse? = null
        var trackResponse: TrackResponse? = null
    }
}

class NetworkViewModel(/*private*/ val musicRepository: MusicRepository) : ViewModel() {
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
     * Gets Albums information from the Spotify API Retrofit service
     */
    fun getApiAlbums() {

        viewModelScope.launch {
            _isLoadingAlbums.value = true
            networkUiState = try {
                //val networkMusicRepository = NetworkMusicRepository()
                getSpotifyToken(musicRepository)
                NetworkUiState.albumsResponse = musicRepository.getAlbums(NetworkUiState.token, 20)
                _isLoadingAlbums.value = false
                NetworkUiState.Success(NetworkUiState.albumsResponse!!)
            } catch (e: IOException) {
                _isLoadingAlbums.emit(false)
                Log.d("RESPONSE", e.message.toString())
                NetworkUiState.Error
            } catch (e: HttpException) {
                _isLoadingAlbums.emit(false)
                Log.d("RESPONSE", e.response()?.errorBody()?.string().toString())
                NetworkUiState.Error
            }
        }
    }


   /* fun getApiAlbumInfo() {


        viewModelScope.launch {
            try {
                withContext(Dispatchers.IO) {
                    tracks = networkViewModel.musicRepository.getAlbumTracks(
                        album.id,
                        NetworkUiState.token
                    )
                }
            } catch (ex: UnknownHostException) {
                //ErrorScreen(pullRefreshState = , refreshing = , errorMessage = )
            }
        }

    }
*/
    private suspend fun getSpotifyToken(networkMusicRepository: MusicRepository) {
        val responseCall = networkMusicRepository.getToken()
        NetworkUiState.token = "Bearer " + responseCall.accessToken
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

