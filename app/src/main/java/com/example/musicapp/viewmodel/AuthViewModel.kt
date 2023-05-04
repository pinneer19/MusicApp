package com.example.musicapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.musicapp.MusicApplication
import com.example.musicapp.data.auth.AuthRepository
import com.example.musicapp.data.auth.Resource
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

data class AuthState(
    val isLoading: Boolean = false,
    val isSuccess: String? = "",
    val isError: String? = ""
)

class AuthViewModel(private val repository: AuthRepository) : ViewModel() {


    private val _loginState = MutableStateFlow<Resource<FirebaseUser>?>(null)
    val loginState: StateFlow<Resource<FirebaseUser>?> = _loginState

    private val _signupState = MutableStateFlow<Resource<FirebaseUser>?>(null)
    val signupState: StateFlow<Resource<FirebaseUser>?> = _signupState


    private val _googleState = MutableStateFlow<Resource<FirebaseUser>?>(null)
    val googleState: StateFlow<Resource<FirebaseUser>?> = _googleState

    val currentUser: FirebaseUser?
        get() = repository.currentUser

    init {
        if (repository.currentUser != null) {
            if (repository.currentUser!!.displayName.isNullOrEmpty())
                _loginState.value = Resource.Success(repository.currentUser!!)
            else _googleState.value = Resource.Success(repository.currentUser!!)
        }
    }
    fun googleSignIn(credential: AuthCredential) {
        viewModelScope.launch {
            _googleState.value = Resource.Loading()
            val result = repository.googleSignIn(credential)
            _googleState.value = result
        }
    }

    fun loginUser(email: String, password: String) {
        viewModelScope.launch {
            _loginState.value = Resource.Loading()
            val result = repository.loginUser(email, password)

            _loginState.value = result
        }
    }


    fun registerUser(email: String, password: String) {
        viewModelScope.launch {
            _signupState.value = Resource.Loading()
            val result = repository.registerUser(email, password)
            _signupState.value = result
        }
    }

    fun signOutUser() {
        repository.signOut()
        _googleState.value = null
        _loginState.value = null
        _signupState.value = null
    }


    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application =
                    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as MusicApplication)
                val authRepository = application.container.authRepository
                AuthViewModel(repository = authRepository)
            }
        }
    }
}