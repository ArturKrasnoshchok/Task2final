package com.example.task2.contacts

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.task2.storage.UserDataBase

class ContactsViewModelFactory : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ContactsViewModel(
            userDataBase = UserDataBase(),
            mapper = UserToContactMapper()
        ) as T
    }
}