package com.example.mvvmdemo.api


import com.example.mvvmdemo.model.Register
import com.example.mvvmdemo.model.RegisterResponse
import retrofit2.Response
import retrofit2.http.POST
import retrofit2.http.Query

interface RegisterService {
    @POST("register")
    suspend fun registerUser(
        @Query("name")
        name: String,
        @Query("email")
        email: String,
        @Query("phone")
        phone: String,
        @Query("password")
        password: String,
        @Query("password_confirmation")
        password_confirmation: String
    ): Response<RegisterResponse>
}