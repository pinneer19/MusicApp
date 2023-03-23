package com.example.musicapp.data

import com.example.musicapp.network.SpotifyApiService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

interface AppContainer {
    val musicApiRepository: MusicRepository


}

class MusicAppContainer() : AppContainer {
    private val BASE_URL_FOR_TOKEN = "https://accounts.spotify.com/"
    private val BASE_URL = "https://api.spotify.com/v1/"

    private val retrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(BASE_URL)
        .build()

    private val retrofit_token: Retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(BASE_URL_FOR_TOKEN)
        .build()

    private val retrofitService: SpotifyApiService by lazy {
        retrofit.create(SpotifyApiService::class.java)
    }

    private val retrofitTokenService: SpotifyApiService by lazy {
        retrofit_token.create(SpotifyApiService::class.java)
    }


    override val musicApiRepository: MusicRepository by lazy {
        NetworkMusicRepository(retrofitService, retrofitTokenService)
    }

}
