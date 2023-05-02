package com.example.musicapp.data

import com.example.musicapp.R
import com.example.musicapp.ui.navigation.BottomNavItem
import com.example.musicapp.ui.navigation.NavRoutes
import kotlin.time.Duration

object BottomNavItems {
    val list = listOf(
        BottomNavItem(
            name = "Home",
            route = NavRoutes.Start.name,
            icon = R.drawable.outline_home,
            icon_focused = R.drawable.home
        ),
        BottomNavItem(
            name = "Favourite",
            route = NavRoutes.Favourites.name,
            icon = R.drawable.favourite,
            icon_focused = R.drawable.filled_favourite,
        ),
        BottomNavItem(
            name = "Playlists",
            route = NavRoutes.Playlists.name,
            icon = R.drawable.outline_library_music,
            icon_focused = R.drawable.playlist
        ),
        BottomNavItem(
            name = "Settings",
            route = NavRoutes.Settings.name,
            icon = R.drawable.outline_settings_24,
            icon_focused = R.drawable.settings
        )
    )
}