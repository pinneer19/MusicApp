package com.example.musicapp

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.Scaffold
import androidx.navigation.compose.rememberNavController
import com.example.musicapp.data.BottomNavItems
import com.example.musicapp.ui.navigation.BottomBarNavigation
import com.example.musicapp.ui.navigation.Navigation
import com.example.musicapp.ui.theme.MusicAppTheme

class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterialScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MusicAppTheme {


                val navController = rememberNavController()
                Scaffold(
                    bottomBar = {
                        BottomBarNavigation(
                            items = BottomNavItems.list,
                            navController = navController,
                            onItemClicked = { navController.navigate(it.route) }
                        )
                    }
                ) {
                    Navigation(
                        navController = navController
                    )
                }
            }
        }
    }
}