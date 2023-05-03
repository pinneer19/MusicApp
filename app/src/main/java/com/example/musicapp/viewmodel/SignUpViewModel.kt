package com.example.musicapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.musicapp.MusicApplication
import com.example.musicapp.data.auth.AuthRepository
import com.example.musicapp.data.auth.Resource
import com.example.musicapp.ui.signupScreen.SignUpState
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch



class SignUpViewModel(private val repository: AuthRepository): ViewModel() {
    private val _signUpState = Channel<SignUpState>()
    val signUpState = _signUpState.receiveAsFlow()

    fun registerUser(email: String, password: String) {
        viewModelScope.launch {
            repository.registerUser(email, password).collect { result ->
                when(result) {
                    is Resource.Success -> {
                        _signUpState.send(SignUpState(isSuccess = "Sign in success"))
                    }
                    is Resource.Loading -> {
                        _signUpState.send(SignUpState(isLoading = true))
                    }
                    is Resource.Error -> {
                        _signUpState.send(SignUpState(isError = result.message))
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
                SignUpViewModel(repository = authRepository)
            }
        }
    }
}