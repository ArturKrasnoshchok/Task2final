package com.example.task2.storage.models

data class UserProfile(
    val firsName: String,
    val lastName: String,
    val photo: String = "https://www.facebook.com/photo/?fbid=112754140580140&set=a.112754157246805",
    val career: String = "android developer",
    val email: String="",
    val address: String = "London",
    val birthDate: String = "06.12.1988",
    val password: String=""
)
