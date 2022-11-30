package com.example.task2.storage.models

import android.content.Context

class UserStore(context: Context) {
    companion object {
        private const val FIRSTNAME_ARG = "FIRSTNAME"
        private const val LASTNAME_ARG = "LASTNAME"
        private const val EMAIL_ARG = "EMAIL"
        private const val PASSWORD_ARG = "PASSWORD"
        private const val PREFERENCE_NAME = "UserStore"
    }

    private val sharedPreferences = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)

    fun saveUser(user: UserProfile) {
        sharedPreferences.edit()
            .putString(FIRSTNAME_ARG, user.firsName)
            .putString(LASTNAME_ARG, user.lastName)
            .putString(EMAIL_ARG, user.email)
            .putString(PASSWORD_ARG, user.password)
            .apply()
    }

    fun getCurrentUser(): UserProfile? {
        val firstName = sharedPreferences.getString(FIRSTNAME_ARG, null) ?: return null
        val lastName = sharedPreferences.getString(LASTNAME_ARG, null) ?: return null
        val email = sharedPreferences.getString(EMAIL_ARG, null) ?: return null
        val password = sharedPreferences.getString(PASSWORD_ARG, null) ?: return null
        return UserProfile(firsName = firstName, lastName = lastName, email = email, password = password)
    }
}