package com.example.musicapp.model

@kotlinx.serialization.Serializable
data class Albums(
    val limit: Int,
    val next: String,
    val previous: String,
    val total: Int,
    val items: List<Album>
)

@kotlinx.serialization.Serializable
data class Album(
    val album_type: String,
    val total_tracks: Int,
    val href: String,
    val id: String,
    val images: List<Image>,
    val name: String,
    val release_date: String,
    val uri: String,
    val artists: List<Artist>
)