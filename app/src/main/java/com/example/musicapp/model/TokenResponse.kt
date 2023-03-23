package com.example.musicapp.model

import com.google.gson.annotations.SerializedName


@kotlinx.serialization.Serializable
data class TokenResponse(
    @SerializedName("access_token")
    val accessToken: String,
    @SerializedName("token_type")
    val tokenType: String,
    @SerializedName("expires_in")
    val expiresIn: Int
)