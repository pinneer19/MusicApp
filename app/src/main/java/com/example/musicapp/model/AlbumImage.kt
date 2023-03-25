package com.example.musicapp.model

@kotlinx.serialization.Serializable
data class Image(
    val height: Int,
    val url: String,
    val width: Int
)