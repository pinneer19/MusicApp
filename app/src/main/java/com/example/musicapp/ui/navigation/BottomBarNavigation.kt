package com.example.musicapp.ui.navigation

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState

@Composable
fun BottomBarNavigation(
    items: List<BottomNavItem>,
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    val backStackEntry = navController.currentBackStackEntryAsState()
    val route = backStackEntry.value?.destination?.route


    val bottomBarDestination = items.any { it.route == route } || route == NavRoutes.Album.name + "/{album}"
    if (bottomBarDestination) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .height(56.dp)
                .background(MaterialTheme.colors.background),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = CenterVertically
        ) {

            items.forEach { item ->
                AddItem(
                    item = item,
                    route = route,
                    navController = navController
                )
            }
        }
    }
}