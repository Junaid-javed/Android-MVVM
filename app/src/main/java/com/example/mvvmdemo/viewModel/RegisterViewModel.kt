package com.example.mvvmdemo.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mvvmdemo.model.Register
import com.example.mvvmdemo.model.RegisterResponse
import com.example.mvvmdemo.repository.RegisterRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody

class RegisterViewModel (private val registerRepository: RegisterRepository): ViewModel() {

    fun registerUser( name: String,
                      email: String,
                      phone: String,
                      password: String,
                      passwordConfirmation: String){

        viewModelScope.launch {
            withContext(Dispatchers.IO){
                registerRepository.registerUser(name, email, phone, password, passwordConfirmation)
            }
        }
    }

    val registerUser: LiveData<RegisterResponse>
        get() = registerRepository.registerData

    val errorData: LiveData<String>
        get() = registerRepository.errorData

}