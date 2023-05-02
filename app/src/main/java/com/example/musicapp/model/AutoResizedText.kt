package com.example.musicapp.model

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.width
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.isUnspecified
import org.w3c.dom.Text


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AutoResizedText(
    text: String,
    style: TextStyle = MaterialTheme.typography.body1,
    modifier: Modifier = Modifier,
    color: Color = style.color,
    isWidthOverflow: Boolean = false
) {
    var resizedTextStyle by remember {
        mutableStateOf(style)
    }
    var shouldDraw by remember {
        mutableStateOf(false)
    }

    val defaultFontSize = style.fontSize

    Text(
        text = text,
        color = color,
        modifier = modifier
            .drawWithContent {
                if (shouldDraw) {
                    drawContent()
                }
            }
            .basicMarquee(delayMillis = 0, velocity = if(isWidthOverflow) 35.dp else 30.dp),
        softWrap = false,
        style = resizedTextStyle,
        onTextLayout = { result ->
            when (isWidthOverflow) {
                false -> {
                    if (result.didOverflowHeight) {
                        if (style.fontSize.isUnspecified) {
                            resizedTextStyle = resizedTextStyle.copy(
                                fontSize = defaultFontSize
                            )
                        }
                        resizedTextStyle = resizedTextStyle.copy(
                            fontSize = resizedTextStyle.fontSize * 0.95
                        )
                    } else {
                        shouldDraw = true
                    }
                }
                true -> {
                    if (result.didOverflowWidth) {
                        if (style.fontSize.isUnspecified) {
                            resizedTextStyle = resizedTextStyle.copy(
                                fontSize = defaultFontSize
                            )
                        }
                        resizedTextStyle = resizedTextStyle.copy(
                            fontSize = resizedTextStyle.fontSize * 0.9
                        )
                    } else {
                        shouldDraw = true
                    }
                }
            }

        }
    )
}