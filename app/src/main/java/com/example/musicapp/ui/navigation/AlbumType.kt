package com.example.musicapp.ui.navigation

import android.os.Bundle
import androidx.navigation.NavType
import com.example.musicapp.model.Album
import com.google.gson.Gson

abstract class JsonNavType<T> : NavType<T>(isNullableAllowed = false) {
    abstract fun fromJsonParse(value: String): T
    abstract fun T.getJsonParse(): String

    override fun get(bundle: Bundle, key: String): T? =
        bundle.getString(key)?.let { parseValue(it) }

    override fun parseValue(value: String): T = fromJsonParse(value)

    override fun put(bundle: Bundle, key: String, value: T) {
        bundle.putString(key, value.getJsonParse())
    }
}

class ProfileArgType : JsonNavType<Album>() {
    override fun fromJsonParse(value: String): Album = Gson().fromJson(value, Album::class.java)
    override fun Album.getJsonParse(): String {
        return Gson().toJson(this)
    }
}