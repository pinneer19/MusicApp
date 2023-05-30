package com.example.musicapp.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@kotlinx.serialization.Serializable
data class Track(
    val title: String,
    @SerializedName("duration") val duration: Int?,
    val preview: String,
    val artist: Artist,
    val album: Album?,
    val id: Long = 0
)