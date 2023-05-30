package com.example.musicapp.viewmodel

import android.net.Uri
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import com.example.musicapp.MusicApplication
import com.example.musicapp.data.music.MusicRepository
import com.example.musicapp.model.Track
import kotlinx.coroutines.flow.*

data class MusicUiState(
    val isPlaying: Boolean = false,
    val playlist: List<Track> = emptyList(),
    val currentTrackIndex: Int = 0,
    val currentPosition: Long = 0,
)

class MusicViewModel(private val musicRepository: MusicRepository) : ViewModel() {

    private val _musicUiState = MutableStateFlow(MusicUiState())
    val musicUiState: StateFlow<MusicUiState> = _musicUiState.asStateFlow()

    val player: ExoPlayer = musicRepository.getExoPlayerInstance()

    private val _handler = Handler(Looper.getMainLooper())
    init {
        player.repeatMode = ExoPlayer.REPEAT_MODE_ALL


        player.addListener(
            object : Player.Listener {
                override fun onMediaItemTransition(mediaItem: MediaItem?, reason: Int) {
                    super.onMediaItemTransition(mediaItem, reason)
                    _musicUiState.update { it.copy(currentTrackIndex = player.currentMediaItemIndex) }
                }
            }
        )
    }

    fun updatePlaylist(playlist: List<Track>) {

        player.stop()
        player.clearMediaItems()
        playlist.forEach {
            player.addMediaItem(MediaItem.fromUri(Uri.parse(it.preview)))
        }
        _musicUiState.update { state -> state.copy(isPlaying = false, playlist = playlist, currentPosition = 0L, currentTrackIndex = 0) }
        player.prepare()
        player.seekTo(0, 0L)
    }

    fun play() {
        _musicUiState.update { it.copy(isPlaying = true, currentTrackIndex = player.currentMediaItemIndex) }
        player.play()

        _handler.postDelayed(object: Runnable {
            override fun run() {
                try {
                    _musicUiState.update { it.copy(currentPosition = player.currentPosition) }
                    _handler.postDelayed(this, 0)
                }
                catch (ex: Exception) {
                    _musicUiState.update { it.copy(currentPosition = 0L) }
                }
            }
        }, 0)
    }
    fun pause() {
        _musicUiState.update { it.copy(isPlaying = false) }
        player.pause()
    }


    fun updatePlayingStatus(status: Boolean) {
        _musicUiState.update { state -> state.copy(isPlaying = status) }
    }

    fun updateIndex(index: Int) {
        _musicUiState.update { state -> state.copy(currentTrackIndex = index) }
    }

    fun loadTrack(index: Int) {
        _musicUiState.update { it.copy(isPlaying = true, currentTrackIndex = index) }
        player.seekTo(index, 0L)
        player.play()

        _handler.postDelayed(object: Runnable {
            override fun run() {
                try {
                    _musicUiState.update { it.copy(currentPosition = player.currentPosition) }
                    _handler.postDelayed(this, 0)
                }
                catch (ex: Exception) {
                    _musicUiState.update { it.copy(currentPosition = 0L) }
                }
            }
        }, 0)
    }

    private fun stop() {
        player.stop()
        player.release()
        _musicUiState.update { it.copy(isPlaying = false, currentPosition = 0L) }
    }
    fun playNextTrack() {
        player.seekToNextMediaItem()
        _musicUiState.update { state -> state.copy(isPlaying = true, currentTrackIndex = player.currentMediaItemIndex) }
        player.play()
    }


    fun playPreviousTrack() {
        player.seekToPreviousMediaItem()
        _musicUiState.update { state -> state.copy(isPlaying = true, currentTrackIndex = player.currentMediaItemIndex) }
        player.play()
    }

    fun seek(position: Long) {
        player.seekTo(position)
        _musicUiState.update {
            state -> state.copy(isPlaying = true, currentPosition = position)
        }
        player.play()
    }
    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application =
                    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as MusicApplication)
                val metaDataRepository = application.container.playerRepository
                MusicViewModel(musicRepository = metaDataRepository)
            }
        }
    }
}