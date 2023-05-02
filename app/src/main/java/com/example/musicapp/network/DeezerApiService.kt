package com.example.musicapp.network

import com.example.musicapp.model.*
import retrofit2.http.*

interface DeezerApiService {
    @GET("chart/0/playlists")
    suspend fun getPlaylists(
        @Query("limit") limit: Int
    ): PlaylistsResponse

    @GET("playlist/{id}/tracks")
    suspend fun getPlaylistTracks(
        @Path("id") playlistId: String
    ): PlaylistTracksResponse
}