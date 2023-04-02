package com.example.musicapp.network

import android.util.Log
import androidx.compose.runtime.collectAsState
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
import com.example.musicapp.data.MusicRepository
import com.example.musicapp.data.NetworkMusicRepository
import com.example.musicapp.model.AlbumsResponse
import com.example.musicapp.model.TokenResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.*
import java.io.IOException

sealed interface NetworkUiState {
    data class Success(val albumsResponse: AlbumsResponse) : NetworkUiState
    object Error : NetworkUiState
    object Loading : NetworkUiState
    companion object {
        var token: String = ""
        var albumsResponse: AlbumsResponse? = null
    }
}

class NetworkViewModel(/*private*/ val musicRepository: MusicRepository) : ViewModel() {
    /** The mutable State that stores the status of the most recent request */
    var networkUiState: NetworkUiState by mutableStateOf(NetworkUiState.Loading)
        private set

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()


    init {
        getApiAlbums()
    }

    /**
     * Gets Albums information from the Spotify API Retrofit service
     */
    fun getApiAlbums() {

        viewModelScope.launch {
            _isLoading.value = true
            networkUiState = try {
                //val networkMusicRepository = NetworkMusicRepository()
                getSpotifyToken(musicRepository)
                NetworkUiState.albumsResponse = musicRepository.getAlbums(NetworkUiState.token, 20)
                _isLoading.value = false
                NetworkUiState.Success(NetworkUiState.albumsResponse!!)
            } catch (e: IOException) {
                _isLoading.emit(false)
                Log.d("RESPONSE", e.message.toString())
                NetworkUiState.Error
            } catch (e: HttpException) {
                _isLoading.emit(false)
                Log.d("RESPONSE", e.response()?.errorBody()?.string().toString())
                NetworkUiState.Error
            }
        }
    }

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

