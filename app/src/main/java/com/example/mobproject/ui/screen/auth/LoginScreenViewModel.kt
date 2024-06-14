package com.example.mobproject.ui.screen.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobproject.repository.AuthRepository
import com.example.mobproject.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginScreenViewModel @Inject constructor(
    private val repository: AuthRepository
) : ViewModel(){
    val _loginState = Channel<LoginState>()
    val loginState = _loginState.receiveAsFlow()

    var message = ""

    fun register(email: String, password: String) = viewModelScope.launch{
        repository.register(email, password).collect{
            message = it.data.toString()

        }
    }

    fun login(email: String, password: String) = viewModelScope.launch {
        repository.login(email, password).collect {
            when(it){
                is Resource.Loading -> {
                    _loginState.send(LoginState(isLoading = true))
                }
                is Resource.Success -> {
                    _loginState.send(LoginState(isSuccess = "Login Success"))
                }
                is Resource.Error -> {
                    _loginState.send(LoginState(isError = it.message ?: "An unknown error occurred"))
                }
            }
        }
    }
}