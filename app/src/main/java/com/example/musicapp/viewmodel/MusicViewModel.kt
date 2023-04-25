import android.net.Uri
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.media3.common.AudioAttributes
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import com.example.musicapp.MusicApplication
import com.example.musicapp.data.MusicRepository
import com.example.musicapp.model.Playlist
import com.example.musicapp.model.PlaylistTracksResponse
import com.example.musicapp.model.Track
import kotlinx.coroutines.flow.*

data class MusicUiState(
    val isPlaying: Boolean = false,
    val playlist: List<Track> = emptyList(),
    val currentTrackIndex: Int = 0,
)

class MusicViewModel(private val musicRepository: MusicRepository) : ViewModel() {

    private val _musicUiState = MutableStateFlow(MusicUiState())
    val musicUiState: StateFlow<MusicUiState> = _musicUiState.asStateFlow()


    var player: ExoPlayer = musicRepository.getExoPlayerInstance()

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

        player.clearMediaItems()
        playlist.forEach {
            player.addMediaItem(MediaItem.fromUri(Uri.parse(it.preview)))
        }
        _musicUiState.update { state -> state.copy(isPlaying = false, playlist = playlist) }
        player.prepare()
        player.seekTo(0, 0L)
    }

    fun play() {
        _musicUiState.update { it.copy(isPlaying = true) }
        player.play()
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
    }

    private fun stop() {
        player.stop()
        player.release()
        _musicUiState.update { it.copy(isPlaying = false) }
    }
    fun playNextTrack() {

        player.seekToNextMediaItem()

        _musicUiState.update { state -> state.copy(isPlaying = true, currentTrackIndex = player.currentMediaItemIndex) }
        Log.i("PLAYER", "PLAY NEXT AUDIO")
        player.play()

    }

    private fun seek(position: Long) {
        player.seekTo(position)
    }
    fun playPreviousTrack() {

        player.seekToPreviousMediaItem()
        _musicUiState.update { state -> state.copy(isPlaying = true, currentTrackIndex = player.currentMediaItemIndex) }
        Log.i("PLAYER", "PLAY PREViOUS AUDIO")
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