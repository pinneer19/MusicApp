package com.example.musicapp.model

import android.net.Uri
import com.example.musicapp.model.Artist
import com.google.gson.Gson

@kotlinx.serialization.Serializable
data class AlbumsResponse(
    val albums: Albums
)
@kotlinx.serialization.Serializable
data class Albums(
    val items: List<Album>,
    val uri: String,
    val limit: Int,
    val next: String,
    val offset: Int,
    val previous: String,
    val total: Int
)
@kotlinx.serialization.Serializable
data class Album(
    val album_group: String,
    val album_type: String,
    val artists: List<Artist>,
    val available_markets: List<String>,
    val external_urls: ExternalUrls,
    val href: String,
    val id: String,
    val images: List<Image>,
    val name: String,
    val release_date: String,
    val release_date_precision: String,
    val total_tracks: Int,
    val type: String,
    val uri: String
) {
    override fun toString(): String = Uri.encode(Gson().toJson(this))
}