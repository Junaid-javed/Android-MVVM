package com.example.mvvmdemo.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.mvvmdemo.api.RegisterService
import com.example.mvvmdemo.model.Register
import com.example.mvvmdemo.model.RegisterResponse
import okhttp3.ResponseBody

class RegisterRepository(private val registerService: RegisterService) {

    private val registerLiveData = MutableLiveData<RegisterResponse>()
    private val errorLiveData = MutableLiveData<String>()

    val registerData: LiveData<RegisterResponse> get() = registerLiveData
    val errorData: LiveData<String> get() = errorLiveData

    suspend fun registerUser(
        name: String,
        email: String,
        phone: String,
        password: String,
        passwordConfirmation: String
    ) {
        val result = registerService.registerUser(name,email, phone, password, passwordConfirmation)
        if (result.isSuccessful) {
            val responseBody = result.body()
            registerLiveData.postValue(responseBody!!)
        }else{
            val errorBody = result.errorBody()?.string()
            errorLiveData.postValue(errorBody ?: "Unknown error")
        }
    }
}