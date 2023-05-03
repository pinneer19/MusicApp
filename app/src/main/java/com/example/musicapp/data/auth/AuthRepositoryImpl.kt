package com.example.musicapp.data.auth

import android.content.SharedPreferences
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await

class AuthRepositoryImpl(
    private val firebaseAuth: FirebaseAuth,
    private val sharedPreferences: SharedPreferences
) : AuthRepository {
    override fun loginUser(email: String, password: String): Flow<Resource<AuthResult>> {

        //sharedPreferences.edit().putBoolean("isAuthenticated", true).apply()

        return flow {
            emit(Resource.Loading())
            val result = firebaseAuth.signInWithEmailAndPassword(email, password).await()
            emit(Resource.Success(result))
        }.catch {
            emit(Resource.Error(it.message.toString()))
        }
    }

    override fun registerUser(email: String, password: String): Flow<Resource<AuthResult>> {
        return flow {
            emit(Resource.Loading())
            val result = firebaseAuth.createUserWithEmailAndPassword(email, password).await()
            emit(Resource.Success(result))
        }.catch {
            emit(Resource.Error(it.message.toString()))
        }
    }

    override fun googleSignIn(credential: AuthCredential): Flow<Resource<AuthResult>> {

        //sharedPreferences.edit().putBoolean("isAuthenticated", true).apply()

        return flow {
            emit(Resource.Loading())
            val result = firebaseAuth.signInWithCredential(credential).await()

            emit(Resource.Success(result))
        }.catch {
            emit(Resource.Error(it.message.toString()))
        }
    }

    override fun userIsAuthenticated(): Boolean {
        //val a = sharedPreferences.getBoolean("isAuthenticated", false)
        return firebaseAuth.currentUser != null
    }

    override fun signOut() {
        //sharedPreferences.edit().putBoolean("isAuthenticated", false).apply()
        firebaseAuth.signOut()
    }

}