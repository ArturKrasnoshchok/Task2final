package com.example.task2.ui

import android.content.Context
import com.example.task2.storage.models.UserProfile
import com.example.task2.storage.models.UserStore
import com.example.task2.util.AuthView
import java.util.*

class AuthPresenter(
    private val view: AuthView,
    private val context: Context,
    private val store: UserStore,
) {
    private var shouldRememberUser: Boolean = false

    init {
        val currentUser = store.getCurrentUser()
        if (currentUser != null) {
            navigateToMain(currentUser)
        }
    }

    private fun navigateToMain(userProfile: UserProfile) {
        context.startActivity(ContactsActivity.getIntent(context, userProfile.firsName, userProfile.lastName))
    }

    fun sighUp(email: String, password: String) {
        if (!checkEmail(email)) {
            view.showIncorrectEmail()
        } else if (!checkPassword(password)) {
            view.showIncorrectPassword()
        } else {
            performSignUp(email, password)
        }
    }

    private fun performSignUp(email: String, password: String) {
        val (firsName, lastName) = parseUser(email)
        val userProfile = UserProfile(firsName = firsName, lastName = lastName, email = email, password = password)
        if (shouldRememberUser) {
            store.saveUser(userProfile)
        }
        navigateToMain(userProfile)
    }

    private fun parseUser(email: String): Pair<String, String> {
        val firstName = email.substringBefore(".").replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }
        val lastName = email.substringAfter(".").substringBefore("@")
            .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }
        return firstName to lastName
    }

    fun rememberUser(value: Boolean) {
        shouldRememberUser = value
    }

    private fun checkEmail(email: String): Boolean {
        return (email.contains("@") && email.contains(".") &&
                email.contains(" ").not() && email.isEmpty().not())
    }

    private fun checkPassword(password: String): Boolean {
        return password.any { it.isDigit() } && password.any { it.isLetter() }
                && (password.length < 17)
    }
}