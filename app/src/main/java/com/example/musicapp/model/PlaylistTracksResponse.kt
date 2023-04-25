package com.example.musicapp.model

@kotlinx.serialization.Serializable
data class PlaylistTracksResponse(
    val data: List<Track>,
    val checksum: String,
    val total: Int,
    val next: String
)
