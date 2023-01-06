package com.example.task2.api

import com.example.task2.api.request.LoginRequest
import com.example.task2.api.response.LoginResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiInterface {
    @POST("/login")
    suspend fun registerAccount(@Body request: LoginRequest): Response<LoginResponse>
}