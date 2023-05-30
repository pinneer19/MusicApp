package com.example.musicapp.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@kotlinx.serialization.Serializable
data class Artist(
    val name: String,
)