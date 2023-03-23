package com.example.musicapp.model

@kotlinx.serialization.Serializable
data class Image(
    val url: String,
    val height: Int,
    val width: Int
)