package com.example.mvvmdemo.model

import com.google.gson.annotations.SerializedName

data class Register(
    @SerializedName("name")
    val userName: String,

    @SerializedName("email")
    val userEmail: String,

    @SerializedName("phone")
    val phone: String,

    @SerializedName("password")
    val password: String,

    @SerializedName("password_confirmation")
    val passwordConfirmation: String
)