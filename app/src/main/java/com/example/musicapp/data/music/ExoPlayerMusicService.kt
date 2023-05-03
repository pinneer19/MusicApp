package com.example.musicapp.data.music

import android.app.Application
import androidx.media3.exoplayer.ExoPlayer


class ExoPlayerMusicService(private val app: Application) : MusicService {
    private val exoPlayer: ExoPlayer by lazy {
        ExoPlayer.Builder(app).build()
    }

    override fun getPlayer(): ExoPlayer {
        return exoPlayer
    }
}