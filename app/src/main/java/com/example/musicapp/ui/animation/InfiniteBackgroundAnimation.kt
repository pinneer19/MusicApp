package com.example.musicapp.ui.animation

import android.annotation.SuppressLint
import androidx.compose.animation.animateColor
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.musicapp.R

@SuppressLint("ModifierFactoryUnreferencedReceiver")
fun Modifier.infiniteTransition(fraction: Float) : Modifier = composed {
    val infiniteTransition = rememberInfiniteTransition()

    val color = infiniteTransition.animateColor(
        initialValue = MaterialTheme.colors.secondaryVariant,
        targetValue = MaterialTheme.colors.secondary,
        animationSpec = infiniteRepeatable(
            animation = tween(3000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        )
    )
    Modifier.background(color.value.copy(alpha = fraction))
}
