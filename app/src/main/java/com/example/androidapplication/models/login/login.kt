package com.example.androidapplication.models.login

data class LoginRequest(
    val email: String,
    val password: String
)

data class LoginResponse(
    val status: String,
    val message: String,
    val accessToken: String // Rename this to match your backend response
)
