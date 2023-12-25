package com.example.mvvmdemo.model

data class LoginResponse(
    val success: Boolean,
    val message: String,
    val result: UserResult?,
    val token: String?,
    val errors: List<String>,
    val status: Int
)

data class UserResult(
    val id: Int,
    val name: String,
    val email: String,
    val phone: String,
    val email_verified_at: String?, // You may want to use a proper date type here
    val created_at: String,
    val updated_at: String
)
