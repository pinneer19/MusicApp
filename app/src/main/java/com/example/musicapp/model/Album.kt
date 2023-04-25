package com.example.musicapp.model

import android.net.Uri
import com.example.musicapp.model.Artist
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName

@kotlinx.serialization.Serializable
data class Album(
    val id: Int? = null,
    val title: String? = null,
    val cover: String? = null,
    @SerializedName("cover_small") val coverSmall: String,
    @SerializedName("cover_medium") val coverMedium: String,
    @SerializedName("cover_big") val coverBig: String,
    @SerializedName("cover_xl") val coverXl: String,
    @SerializedName("md5_image") val md5Image: String,
    val tracklist: String,
    val type: String
)