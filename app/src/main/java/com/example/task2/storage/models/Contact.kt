package com.example.task2.storage.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Contact(
    var id: Int,
    val name: String,
    val photoUri: String,
    val career: String,
    val email: String,
    val address: String,
    val birthDate: String,
    val isSelected: Boolean,
):Parcelable






