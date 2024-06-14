package com.example.mobproject.repository

import android.util.Log
import com.example.mobproject.api.AuthService
import com.example.mobproject.util.Resource
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val firebaseAuth: FirebaseAuth
) : AuthService {
    override fun login(email: String, password: String): Flow<Resource<AuthResult>> {
        return flow {
            emit(Resource.Loading())
            try {
                val authResult = firebaseAuth.signInWithEmailAndPassword(email, password).await()
                emit(Resource.Success(authResult))
            } catch (e: Exception) {
                emit(Resource.Error(e.localizedMessage ?: "An unknown error occurred"))
                Log.d("sign", e.localizedMessage)
            }
        }
    }

    override fun register(email: String, password: String): Flow<Resource<AuthResult>> {
        return flow {
            emit(Resource.Loading())
            try {
                val authResult = firebaseAuth.createUserWithEmailAndPassword(email, password).await()
                emit(Resource.Success(authResult))
            } catch (e: Exception) {
                emit(Resource.Error(e.localizedMessage ?: "An unknown error occurred"))
            }
        }
    }

    override fun googleSignIn(credential: AuthCredential): Flow<Resource<AuthResult>> {
        return flow {
            emit(Resource.Loading())
            try {
                val authResult = firebaseAuth.signInWithCredential(credential).await()
                emit(Resource.Success(authResult))
            } catch (e: Exception) {
                emit(Resource.Error(e.localizedMessage ?: "An unknown error occurred"))
            }
        }
    }

    override fun logout() {
        firebaseAuth.signOut()
    }

    override fun checkUserLoggedIn(): Boolean {
        return firebaseAuth.currentUser != null
    }

    companion object{
        fun logout(){
            FirebaseAuth.getInstance().signOut()
        }
    }
}