package com.example.musicapp.model
@kotlinx.serialization.Serializable
data class Track(
    val artists: List<Artist>,
    val duration_ms: Int,
    val name: String,
    val href: String
)
