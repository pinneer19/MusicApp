package com.example.musicapp.network

import androidx.compose.ui.res.stringResource
import com.example.musicapp.R
import com.example.musicapp.model.*
import kotlinx.serialization.ExperimentalSerializationApi
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface SpotifyApiService {


    @Headers("Accept: application/json","Content-Type: application/json")
    @GET("browse/new-releases")
    suspend fun getAlbums(
        @Header("Authorization") authorization: String,
        @Query("limit") limit: Int,
        @Query("country") country: String = "US"
    ): AlbumsResponse


    @GET("albums/{id}")
    suspend fun getAlbumById(
        @Header("Authorization") authorization: String,
        @Query("limit") limit: Int,
    ): AlbumsResponse


    @Headers("Accept: application/json","Content-Type: application/json")
    @GET("albums/{id}/tracks")
    suspend fun getAlbumTracks(
        @Path("id") albumId: String,
        @Header("Authorization") authorization: String
    ): TrackResponse

    @POST("api/token")
    @FormUrlEncoded
    suspend fun getToken(
        @Header("Authorization") auth: String,
        @Header("Content-Type") content: String,
        @Field("grant_type") grantType: String
    ): TokenResponse
}



/**
 * Use the Retrofit builder to build a retrofit object using a kotlinx.serialization converter
 */
