package com.example.musicapp.model

import com.google.gson.annotations.SerializedName



@kotlinx.serialization.Serializable
data class TrackResponse(
    val total: Int,
    val next: String,
    val items: List<Track>
)