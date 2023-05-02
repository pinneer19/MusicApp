package com.example.musicapp

import android.app.Application
import com.example.musicapp.data.AppContainer
import com.example.musicapp.data.MusicAppContainer

class MusicApplication: Application() {
    lateinit var container: AppContainer
    override fun onCreate() {
        super.onCreate()
        container = MusicAppContainer()
    }
}