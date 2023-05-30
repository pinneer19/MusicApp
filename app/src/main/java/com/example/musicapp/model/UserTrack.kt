package com.example.musicapp.model

import com.google.gson.annotations.SerializedName

data class UserTrack(
    val title: String = "",
    val preview: String = "",
    val artist: String = ""
)