package com.example.mvvmdemo.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.mvvmdemo.api.LoginService
import com.example.mvvmdemo.model.Login
import com.example.mvvmdemo.model.LoginResponse

class LoginRepository(private val loginService: LoginService) {

    private val loginLiveData = MutableLiveData<LoginResponse>()
    val loginUser: LiveData<LoginResponse>
        get() = loginLiveData

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> get() = _errorMessage

    suspend fun loginUser(email: String, password: String){

        val result = loginService.loginUser(email,password)

        if (result.isSuccessful) {
            val responseBody = result.body()
            loginLiveData.postValue(responseBody!!)
        }else{
            val errorBody = result.errorBody()?.string()
            _errorMessage.postValue(errorBody ?: "Unknown error")
        }
    }

}