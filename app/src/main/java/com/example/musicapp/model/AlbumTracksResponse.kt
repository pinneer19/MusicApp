package com.example.musicapp.model


@kotlinx.serialization.Serializable
data class AlbumTracksResponse(
    val total: Int,
    val next: String,
    val items: List<Track>
)