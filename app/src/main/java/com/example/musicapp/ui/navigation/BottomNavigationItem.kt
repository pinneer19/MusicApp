package com.example.musicapp.ui.navigation

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import kotlinx.coroutines.launch

@Composable
fun AddItem(
    item: BottomNavItem,
    destination: NavDestination?,
    navController: NavHostController
) {
    //val selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true
    val selected = item.route == destination?.route
    val background =
        if (selected) MaterialTheme.colors.primary.copy(alpha = 0.6f) else Color.Transparent

    val contentColor =
        if (selected) Color.White else Color.Black


    val scaleA = remember {
        Animatable(initialValue = 1f)
    }

    LaunchedEffect(key1 = selected) {
        if (selected) {
            launch {
                scaleA.animateTo(
                    targetValue = 0.8f,
                    animationSpec = tween(50)
                )
                scaleA.animateTo(
                    targetValue = 1f,
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioNoBouncy,
                        stiffness = Spring.StiffnessMediumLow
                    )
                )
            }
        }
    }
    Box(
        modifier = Modifier
            .scale(scale = scaleA.value)
            .height(40.dp)
            .clip(CircleShape)
            .background(background)
            .clickable(onClick = {
                navController.navigate(item.route) {
                    popUpTo(navController.graph.findStartDestination().id)
                    launchSingleTop = true
                }
            })
    ) {
        Row(
            modifier = Modifier
                .padding(start = 10.dp, end = 10.dp, top = 8.dp, bottom = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Icon(
                painter = painterResource(id = if (selected) item.icon_focused else item.icon),
                contentDescription = item.name,
                tint = contentColor
            )
        }
    }
}