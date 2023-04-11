package com.example.musicapp.ui.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
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
    onItemClicked: (BottomNavItem) -> Unit,
    modifier: Modifier = Modifier
) {
    val backStackEntry = navController.currentBackStackEntryAsState()
    Row(
        modifier = Modifier
            .padding(start = 10.dp, end = 10.dp, top = 8.dp, bottom = 8.dp)
            .background(Color.Transparent)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = CenterVertically
    ) {

    items.forEach { item ->
            //val selected = item.route == backStackEntry.value?.destination?.route
        AddItem(
            item = item,
            destination = backStackEntry.value?.destination,
            navController = navController
        )
        /*BottomNavigationItem(
                selected = selected,
                onClick = { onItemClicked(item) },
                icon = {
                    val background =
                        if (selected) MaterialTheme.colors.primary.copy(alpha = 0.6f) else Color.Transparent
                    val contentColor =
                        if (selected) Color.White else Color.Black
                    Box(
                        modifier = Modifier
                            .size(36.dp)
                            .clip(CircleShape)
                            .background(background),
                        contentAlignment = Center
                    ) {
                        Icon(
                            painter = painterResource(id = if (selected) item.icon_focused else item.icon),
                            contentDescription = item.name,
                            tint = contentColor
                        )
                    }
                }
            )*/
        }
    }
}