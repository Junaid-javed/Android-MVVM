package com.example.mvvmdemo.model

import com.google.gson.annotations.SerializedName

data class Login(

    @SerializedName("email")
    val userEmail: String,

    @SerializedName("password")
    val userPassword: String
)
