package com.example.musicapp.network

import androidx.compose.ui.res.stringResource
import com.example.musicapp.R
import com.example.musicapp.model.Album
import com.example.musicapp.model.Albums
import com.example.musicapp.model.TokenResponse
import kotlinx.serialization.ExperimentalSerializationApi
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface SpotifyApiService {

    @Headers("Content-Type: application/json")
    @GET("browse/new-releases")
    suspend fun getAlbums(
        @Header("Authorization") authorization: String,
        @Query("limit") limit: Int,
    ): Albums

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
