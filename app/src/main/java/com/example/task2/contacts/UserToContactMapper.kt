package com.example.task2.contacts

import com.example.task2.storage.models.UserEntity

class UserToContactMapper {

    fun map(user: UserEntity): Contact = with(user) {
        return Contact(
            id = id,
            name = name,
            photoUri = photo,
            career = career,
            email = email,
            address = address,
            birthDate = birthDate,
            isSelected = false,
        )
    }

    fun mapList(users: List<UserEntity>): List<Contact> {
        return users.map { userEntity -> map(userEntity) }
    }
}