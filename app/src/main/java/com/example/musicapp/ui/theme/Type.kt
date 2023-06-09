package com.example.musicapp.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.musicapp.R

// Set of Material typography styles to start with
val Gotham = FontFamily(
    //Font(R.font.gotham_bold, FontWeight.Bold),
    Font(R.font.gotham_light, FontWeight.Light),
    Font(R.font.gotham_medium, FontWeight.Normal),
    Font(R.font.gotham_bold_italic, FontWeight.Bold),
    Font(R.font.gotham_bold, FontWeight.ExtraBold),
    Font(R.font.gotham_book, FontWeight.Thin)
)



val Typography = Typography(
    body1 = TextStyle(
        fontFamily = Gotham,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    ),
    h2 = TextStyle(
        fontFamily = Gotham,
        fontWeight = FontWeight.Bold,
        fontSize = 22.sp
    ),
    h1 = TextStyle(
        fontFamily = Gotham,
        fontWeight = FontWeight.ExtraBold,
        fontSize = 45.sp
    ),
    subtitle1 = TextStyle(
        fontFamily = Gotham,
        fontWeight = FontWeight.Thin,
        fontSize = 22.sp
    ),
    /* Other default text styles to override
    button = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.W500,
        fontSize = 14.sp
    ),
    caption = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp
    )
    */
)