package com.example.musicapp.ui.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectableGroup

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState


/* TODO( "Add bottom bar color like in theme" ) */

@Composable
fun BottomBarNavigation(
    items: List<BottomNavItem>,
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    val backStackEntry = navController.currentBackStackEntryAsState()

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
                destination = backStackEntry.value?.destination,
                navController = navController
            )
        }
    }
}