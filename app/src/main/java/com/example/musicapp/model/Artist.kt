package com.example.musicapp.model

@kotlinx.serialization.Serializable
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

