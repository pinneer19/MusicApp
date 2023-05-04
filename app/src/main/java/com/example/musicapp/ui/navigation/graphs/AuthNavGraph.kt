package com.example.musicapp.ui.navigation.graphs


import android.util.Log
import android.widget.Toast
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.musicapp.ui.signinScreen.SignInScreen
import com.example.musicapp.ui.signupScreen.SignUpScreen
import com.example.musicapp.viewmodel.AuthViewModel


fun NavGraphBuilder.authNavGraph(navController: NavHostController, viewModel: AuthViewModel) {
    navigation(
        route = Graph.AUTHENTICATION,
        startDestination = AuthScreen.Login.route
    ) {

        composable(route = AuthScreen.Login.route) {
            //val viewModel: SignInViewModel = viewModel(factory = SignInViewModel.Factory)
            /*if(viewModel.checkUserIsLogged()) {
                navController.popBackStack()
                navController.navigate(Graph.HOME)

            }*/
            SignInScreen(viewModel = viewModel, navController = navController)
        }

        composable(route = AuthScreen.SignUp.route) {
            //val viewModel: SignUpViewModel = viewModel(factory = SignUpViewModel.Factory)
            SignUpScreen(viewModel = viewModel, navController = navController)
        }
    }
}

sealed class AuthScreen(val route: String) {
    object Login : AuthScreen(route = "LOGIN")
    object SignUp : AuthScreen(route = "SIGN_UP")
}
