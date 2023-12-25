package com.example.mvvmdemo.model

data class RegisterResponse(
    val success: Boolean,
    val message: String,
    val errors: List<String>,
    val code: String,
    val token: String
)
