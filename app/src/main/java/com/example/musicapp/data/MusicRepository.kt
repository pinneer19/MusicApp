package com.example.musicapp.data

import androidx.media3.exoplayer.ExoPlayer

interface MusicRepository {
    fun getExoPlayerInstance(): ExoPlayer
}

interface MusicService {
    fun getPlayer() : ExoPlayer
}

class MusicRepositoryManager(
    private val musicService: MusicService
) : MusicRepository {

    override fun getExoPlayerInstance(): ExoPlayer {
        return musicService.getPlayer()
    }
}