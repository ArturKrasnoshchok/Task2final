package com.example.task2.api.response

data class LoginResponse(
    val status: String,
    val code: Int,
    val message: String?,
    val data: LoginResponseBody?
)

data class LoginResponseBody(
    val user: String,
    val accessToken: String,
    val refreshToken: String
)