package com.example.musicapp.data

import com.example.musicapp.model.Playlist
import com.example.musicapp.model.PlaylistTracksResponse
import com.example.musicapp.model.PlaylistsResponse
import com.example.musicapp.model.Track
import com.example.musicapp.network.DeezerApiService

interface MusicApiRepository {
    suspend fun getPlaylistTracks(playlistId: String) : List<Track>
    suspend fun getPlaylists(limit: Int) : List<Playlist>
}
//ExoPlayer.Builder(app).build()

class NetworkMusicRepository(
    private val apiService: DeezerApiService
) : MusicApiRepository {
    override suspend fun getPlaylistTracks(playlistId: String): List<Track> {
        return apiService.getPlaylistTracks(playlistId).data.filter { it.preview.isNotEmpty() }
    }
    override suspend fun getPlaylists(limit: Int): List<Playlist> {
        return apiService.getPlaylists(limit).data
    }
}