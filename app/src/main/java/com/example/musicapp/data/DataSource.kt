package com.example.musicapp.data

import com.example.musicapp.R
import com.example.musicapp.ui.navigation.BottomNavItem
import com.example.musicapp.ui.navigation.NavRoutes
import kotlin.time.Duration

object DataSource {
    val trackImageId: Int = R.drawable.trackimage
    val trackTitleId: Int = R.string.track_title
    val trackAuthorId: Int = R.string.track_author
}

object BottomNavItems {
    val list = listOf(
        BottomNavItem(
            name = "Home",
            route = NavRoutes.Start.name,
            icon = R.drawable.home
        ),
        BottomNavItem(
            name = "Favourite",
            route = NavRoutes.Favourites.name,
            icon = R.drawable.favourite
        ),
        BottomNavItem(
            name = "Playlists",
            route = NavRoutes.Playlists.name,
            icon = R.drawable.playlist
        ),
        BottomNavItem(
            name = "Settings",
            route = NavRoutes.Settings.name,
            icon = R.drawable.settings
        )
    )
}