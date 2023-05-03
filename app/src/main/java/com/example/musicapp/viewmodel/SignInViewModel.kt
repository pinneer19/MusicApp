package com.example.musicapp.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.musicapp.MusicApplication
import com.example.musicapp.data.auth.AuthRepository
import com.example.musicapp.data.auth.Resource
import com.example.musicapp.network.NetworkViewModel
import com.example.musicapp.ui.signinScreen.GoogleSignInState
import com.example.musicapp.ui.signinScreen.SignInState
import com.google.firebase.auth.AuthCredential
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class SignInViewModel(private val repository: AuthRepository): ViewModel() {
    private val _signInState = Channel<SignInState>()
    val signInState = _signInState.receiveAsFlow()


    private val _googleState = mutableStateOf(GoogleSignInState())
    val googleState: State<GoogleSignInState> = _googleState

    fun checkUserIsLogged(): Boolean {
        return repository.userIsAuthenticated()
    }

    fun googleSignIn(credential: AuthCredential) {
        viewModelScope.launch {
            repository.googleSignIn(credential).collect { result ->
                when(result) {
                    is Resource.Success -> {
                        _googleState.value = GoogleSignInState(success = result.data)
                    }
                    is Resource.Loading -> {
                        _googleState.value = GoogleSignInState(loading = true)
                    }
                    is Resource.Error -> {
                        _googleState.value = GoogleSignInState(error = result.message!!)
                    }
                }
            }
        }
    }

    fun loginUser(email: String, password: String) {
        viewModelScope.launch {
            repository.loginUser(email, password).collect { result ->
                when(result) {
                    is Resource.Success -> {
                        _signInState.send(SignInState(isSuccess = "Sign in success"))
                    }
                    is Resource.Loading -> {
                        _signInState.send(SignInState(isLoading = true))
                    }
                    is Resource.Error -> {
                        _signInState.send(SignInState(isError = result.message))
                    }
                }
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as MusicApplication)
                val authRepository = application.container.authRepository
                SignInViewModel(repository = authRepository)
            }
        }
    }
}