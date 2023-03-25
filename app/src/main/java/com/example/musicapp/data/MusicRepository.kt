package com.example.musicapp.data

import android.util.Base64
import android.util.Log
import com.example.musicapp.model.Albums
import com.example.musicapp.model.AlbumsResponse
import com.example.musicapp.model.TokenResponse
import com.example.musicapp.network.SpotifyApiService
import java.nio.charset.StandardCharsets

interface MusicRepository {

    // fetches albums from spotify api
    suspend fun getAlbums(auth_token: String, limit: Int): AlbumsResponse

    suspend fun getToken(): TokenResponse
}


class NetworkMusicRepository(
    private val apiService: SpotifyApiService,
    private val tokenApiService: SpotifyApiService
) : MusicRepository {

    private val clientID = "0f25fb398de04e8d822773bd911c2aaa"
    private val clientSECRET = "3de0639db248486c9259156d99a43df5"
    /** Fetches list of albums releases from Spotify API*/
    override suspend fun getAlbums(auth_token: String, limit: Int): AlbumsResponse {
        Log.i("RESPONSE_TOKEN", auth_token)
        return apiService.getAlbums(auth_token, limit)
    }

    override suspend fun getToken(): TokenResponse {
        val base64String = get64BaseString("$clientID:$clientSECRET")
        return tokenApiService.getToken(
            base64String,
            "application/x-www-form-urlencoded",
            "client_credentials"
        )
    }

    private fun get64BaseString(value: String = "$clientID:$clientSECRET"): String {
        return "Basic " + Base64.encodeToString(value.toByteArray(StandardCharsets.UTF_8), Base64.NO_WRAP)
    }
}