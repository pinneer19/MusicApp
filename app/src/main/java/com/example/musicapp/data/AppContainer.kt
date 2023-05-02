package com.example.musicapp.data

import android.app.Application
import androidx.media3.exoplayer.ExoPlayer
import com.example.musicapp.network.DeezerApiService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

interface AppContainer {
    val musicApiRepository: MusicApiRepository
    val playerRepository: MusicRepository
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

    override val musicApiRepository: MusicApiRepository by lazy {
        NetworkMusicRepository(retrofitService)
    }
    override val playerRepository: MusicRepository by lazy {
        MusicRepositoryManager(musicService)
    }


}
