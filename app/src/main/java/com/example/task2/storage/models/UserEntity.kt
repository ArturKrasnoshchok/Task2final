package com.example.task2.storage.models

/**
 * A class with user data
 */
data class UserEntity(
    val id: Int,
    val name: String,
    val photo: String,
    val career: String,
    val email: String,
    val address: String,
    val birthDate: String,
)