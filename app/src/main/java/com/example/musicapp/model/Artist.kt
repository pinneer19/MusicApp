package com.example.musicapp.model

import com.google.gson.annotations.SerializedName

/*@kotlinx.serialization.Serializable
data class Artist(
    val external_urls: ExternalUrls,
    val href: String,
    val id: String,
    val name: String,
    val type: String,
    val uri: String
)

@kotlinx.serialization.Serializable
data class ExternalUrls(
    val spotify: String
)
*/


@kotlinx.serialization.Serializable
data class Artist(

    val id: Int,
    val name: String,
    val link: String,
    val picture: String,
    @SerializedName("picture_small") val pictureSmall: String,
    @SerializedName("picture_medium") val pictureMedium: String,
    @SerializedName("picture_big") val pictureBig: String,
    @SerializedName("picture_xl") val pictureXl: String,
    val tracklist: String,
    val type: String

)