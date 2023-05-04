package com.example.musicapp.ui.navigation.graphs


import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.musicapp.network.NetworkViewModel
import com.example.musicapp.ui.mainScreen.HomeScreen
import com.example.musicapp.viewmodel.AuthViewModel



@Composable
fun RootNavigationGraph(navController: NavHostController, viewModel: AuthViewModel) {
    NavHost(
        navController = navController,
        route = Graph.ROOT,
        startDestination = Graph.AUTHENTICATION
    ) {


        authNavGraph(navController = navController, viewModel = viewModel)
        composable(route = Graph.HOME) {
            val context = LocalContext.current
            HomeScreen(authViewModel = viewModel) {
                Toast.makeText(context, "Signed out", Toast.LENGTH_LONG).show()
                viewModel.signOutUser()
                navController.popBackStack()
                navController.navigate(Graph.AUTHENTICATION)
            }
        }
    }
}

object Graph {
    const val ROOT = "root_graph"
    const val AUTHENTICATION = "auth_graph"
    const val HOME = "home_graph"
        const val DETAILS = "details_graph"
}