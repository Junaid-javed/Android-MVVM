package com.example.mvvmdemo.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mvvmdemo.model.Login
import com.example.mvvmdemo.model.LoginResponse
import com.example.mvvmdemo.repository.LoginRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginViewModel(private val loginRepository: LoginRepository): ViewModel() {


     fun loginUser(email: String, password: String){

        viewModelScope.launch {
            withContext(Dispatchers.IO){
                loginRepository.loginUser(email, password)
            }
        }
    }

    val loginUser: LiveData<LoginResponse>
        get() = loginRepository.loginUser

    val loginError: LiveData<String>
        get() = loginRepository.errorMessage
}