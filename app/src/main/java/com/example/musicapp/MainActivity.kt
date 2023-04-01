package com.example.musicapp

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.*
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.musicapp.data.BottomNavItems
import com.example.musicapp.network.NetworkViewModel
import com.example.musicapp.ui.navigation.BottomBarNavigation
import com.example.musicapp.ui.navigation.Navigation
import com.example.musicapp.ui.theme.MusicAppTheme
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : ComponentActivity() {

    private lateinit var auth: FirebaseAuth
    @OptIn(ExperimentalMaterialApi::class)
    @SuppressLint("UnusedMaterialScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = Firebase.auth
        setContent {
            MusicAppTheme {
                val viewModel: NetworkViewModel = viewModel(factory = NetworkViewModel.Factory)

                val navController = rememberNavController()

                Scaffold(
                    bottomBar = {
                        BottomBarNavigation(
                            items = BottomNavItems.list,
                            navController = navController,
                            onItemClicked = { navController.navigate(it.route) }
                        )
                    },
                    scaffoldState = rememberScaffoldState()
                ) {
                    Navigation(
                        navController = navController,
                        networkViewModel = viewModel
                    )
                }
            }
        }
    }
}