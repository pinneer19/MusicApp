import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class MusicViewModel() : ViewModel() {
    /** The mutable State that stores the status of the most recent request */
    private val _uiState = MutableStateFlow(MusicUiState())
    val musicUiState: StateFlow<MusicUiState> = _uiState.asStateFlow()
}

data class MusicUiState(
    var trackPicked: Boolean = false
)
