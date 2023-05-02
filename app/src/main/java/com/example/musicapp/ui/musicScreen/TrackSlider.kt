package com.example.musicapp.ui.musicScreen

import androidx.compose.foundation.layout.*
import androidx.compose.material.Slider
import androidx.compose.material.SliderDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp


const val MillieSecondsInMinute = 60000
const val MillieSecondsInSecond = 1000

private fun parseSliderValue(state: Int): String {

    val minutes = state / MillieSecondsInMinute
    val seconds = (state - MillieSecondsInMinute * minutes) / MillieSecondsInSecond

    return String.format("%d:%02d", minutes, seconds)
}

@Composable
fun PlayerSlider(duration: Int) {

    // duration in ms
    var sliderState by rememberSaveable { mutableStateOf(0f) }


    val parsedDuration = parseSliderValue(duration)
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                start = 20.dp, end = 20.dp
            )
    ) {
        Slider(
            value = 0f,
            onValueChange = { sliderState = it },
            colors = SliderDefaults.colors(
                thumbColor = Color.Black,
                activeTrackColor = Color.Gray
            ),
            valueRange = 0f..100f
        )
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = parseSliderValue(sliderState.toInt()), color = Color.Black)
            Spacer(modifier = Modifier.weight(1f))
            Text(text = parsedDuration, color = Color.Black)
        }
    }

}