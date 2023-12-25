package com.example.mvvmdemo.api

import com.example.mvvmdemo.model.Login
import com.example.mvvmdemo.model.LoginResponse
import retrofit2.Response
import retrofit2.http.POST
import retrofit2.http.Query

interface LoginService {

    @POST("login")
    suspend fun loginUser(
        @Query("email")
        phoneNo: String,
        @Query("password")
        password: String
    ): Response<LoginResponse>
}