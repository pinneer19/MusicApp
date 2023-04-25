package com.example.musicapp.model

import com.google.gson.annotations.SerializedName

@kotlinx.serialization.Serializable
data class PlaylistsResponse (
    val data  : List<Playlist>,
    val total : Int
)
