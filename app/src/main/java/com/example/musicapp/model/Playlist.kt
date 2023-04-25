package com.example.musicapp.model

import com.google.gson.annotations.SerializedName

@kotlinx.serialization.Serializable
data class User(

    val id: Long,
    val name: String,
    val tracklist: String,
    val type: String

)

@kotlinx.serialization.Serializable
data class Playlist(

    val id: Long,
    val title: String,
    val public: Boolean,
    @SerializedName("nb_tracks") val nbTracks: Int,
    val link: String,
    val picture: String,
    @SerializedName("picture_small") val pictureSmall: String,
    @SerializedName("picture_medium") val pictureMedium: String,
    @SerializedName("picture_big") val pictureBig: String,
    @SerializedName("picture_xl") val pictureXl: String,
    val checksum: String,
    val tracklist: String,
    @SerializedName("creation_date") val creationDate: String,
    @SerializedName("md5_image") val md5Image: String,
    @SerializedName("picture_type") val pictureType: String,
    val user: User,
    val type: String
)