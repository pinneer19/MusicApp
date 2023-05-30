package com.example.musicapp.ui.musicScreen

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Slider
import androidx.compose.material.SliderDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.musicapp.viewmodel.MusicUiState
import com.example.musicapp.viewmodel.MusicViewModel
import kotlin.math.roundToLong

const val MillieSecondsInMinute = 60000
const val MillieSecondsInSecond = 1000

private fun parseSliderValue(state: Long = 3000): String {
    val minutes = state / MillieSecondsInMinute
    val seconds = (state - MillieSecondsInMinute * minutes) / MillieSecondsInSecond
    return String.format("%d:%02d", minutes, seconds)
}

@Composable
fun PlayerSlider(
    musicViewModel: MusicViewModel,
    currentPosition: Long,
    onValueChanged: (Float) -> Unit,
    duration: Long = 30_000
) {

    // duration in ms
    val parsedDuration = parseSliderValue(duration)
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                start = 20.dp, end = 20.dp
            ),
        //horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = parseSliderValue(currentPosition),
            style = MaterialTheme.typography.body1.copy(fontSize = 15.sp),
            color = MaterialTheme.colors.primary,
            modifier = Modifier.width(40.dp)
        )
        Slider(
            value = currentPosition.toFloat(),
            onValueChange = { value ->
                onValueChanged(value)
            },

            colors = SliderDefaults.colors(
                thumbColor = Color.Black,
                activeTrackColor = Color.Gray
            ),
            valueRange = 0f..duration.toFloat(),
            modifier = Modifier.padding(horizontal = 7.dp).weight(1f)
        )
        Text(
            text = parsedDuration,
            style = MaterialTheme.typography.body1.copy(fontSize = 15.sp),
            color = MaterialTheme.colors.primary,
            modifier = Modifier.width(40.dp)
        )
    }

}