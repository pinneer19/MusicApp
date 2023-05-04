package com.example.musicapp.data

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.media3.exoplayer.ExoPlayer
import com.example.musicapp.data.auth.AuthRepository
import com.example.musicapp.data.auth.AuthRepositoryImpl
import com.example.musicapp.data.music.*
import com.example.musicapp.network.DeezerApiService
import com.google.firebase.auth.FirebaseAuth
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

interface AppContainer {
    val musicApiRepository: MusicApiRepository
    val playerRepository: MusicRepository
    val authRepository: AuthRepository
}

class MusicAppContainer(private val app: Application) : AppContainer {
    private val BASE_URL = "https://api.deezer.com/"

    private val loggingInterceptor =
        HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    private val okHttpClient =
        OkHttpClient().newBuilder().addInterceptor(loggingInterceptor).build()

    private val retrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .build()

    private val retrofitService: DeezerApiService by lazy {
        retrofit.create(DeezerApiService::class.java)
    }

    private val player: ExoPlayer = ExoPlayer.Builder(app).build()

    private val musicService: MusicService by lazy {
        ExoPlayerMusicService(app)
    }

    private val firebaseInstance = FirebaseAuth.getInstance()


    override val musicApiRepository: MusicApiRepository by lazy {
        NetworkMusicRepository(retrofitService)
    }
    override val playerRepository: MusicRepository by lazy {
        MusicRepositoryManager(musicService)
    }

    override val authRepository: AuthRepository by lazy {
        AuthRepositoryImpl(firebaseInstance, app)
    }
}
