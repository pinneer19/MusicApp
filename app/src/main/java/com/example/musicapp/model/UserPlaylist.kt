package com.example.musicapp.model

import com.google.gson.annotations.SerializedName

data class UserPlaylist(
    val title: String,
    val tracksAmount: Int,
    var documentId: String,
    val tracklist: List<UserTrack>

) {
    constructor() : this("", 0, "", emptyList())
}