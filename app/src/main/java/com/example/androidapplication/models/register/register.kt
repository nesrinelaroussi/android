package com.example.androidapplication.models.register
data class RegisterRequest(
    val name: String,
    val email: String,
    val password: String
)

data class RegisterResponse(
    val name: String,
    val email: String,
    val _id: String // Backend likely returns the MongoDB user ID
)

