package com.example.musicapp.model

import com.google.gson.annotations.SerializedName

/*@kotlinx.serialization.Serializable
data class Track(
    val artists: List<Artist>,
    val uri: String,
    val duration_ms: Int,
    val name: String,
    val href: String,
    val id: String
)
*/

@kotlinx.serialization.Serializable
data class Track(

    val id: Long,
    val readable: Boolean,
    val title: String,
    @SerializedName("title_short") val titleShort: String,
    @SerializedName("title_version") val titleVersion: String,
    @SerializedName("link") val link: String,
    @SerializedName("duration") val duration: Int,
    val rank: Int,
    @SerializedName("explicit_lyrics") val explicitLyrics: Boolean,
    @SerializedName("explicit_content_lyrics") val explicitContentLyrics: Int,
    @SerializedName("explicit_content_cover") val explicitContentCover: Int,
    val preview: String,
    @SerializedName("md5_image") val md5Image: String,
    @SerializedName("time_add") val timeAdd: Int,
    val artist: Artist,
    val album: Album,
    val type: String
)