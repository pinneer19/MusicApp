package com.example.musicapp.ui.mainScreen

import com.example.musicapp.viewmodel.MusicViewModel
import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.musicapp.data.BottomNavItems
import com.example.musicapp.network.NetworkViewModel
import com.example.musicapp.ui.navigation.BottomBarNavigation
import com.example.musicapp.ui.navigation.graphs.HomeNavGraph
import com.example.musicapp.viewmodel.AuthViewModel


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun HomeScreen(
    navController: NavHostController = rememberNavController(),
    authViewModel: AuthViewModel,
    logOutAction: () -> Unit
) {
    Scaffold(
        bottomBar = {
            BottomBarNavigation(
                items = BottomNavItems.list,
                navController = navController,
                modifier = Modifier.fillMaxWidth()
            )
        },
        scaffoldState = rememberScaffoldState()
    ) {
        val viewModel: NetworkViewModel = viewModel(factory = NetworkViewModel.Factory)
        val trackViewModel: MusicViewModel = viewModel(factory = MusicViewModel.Factory)

        HomeNavGraph(
            navController = navController,
            networkViewModel = viewModel,
            musicViewModel = trackViewModel,
            authViewModel = authViewModel,
            logOutAction = logOutAction
        )
    }
}