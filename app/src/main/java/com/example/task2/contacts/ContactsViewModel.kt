package com.example.task2.contacts

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.task2.storage.UserDataBase
import com.example.task2.storage.models.UserEntity

class ContactsViewModel(
    private val userDataBase: UserDataBase,
    private val mapper: UserToContactMapper,
) : ViewModel() {

    private val _showRemoveContactConfirmation = MutableLiveData<ContactInfo?>()
    val removeContactBanner: LiveData<ContactInfo?> = _showRemoveContactConfirmation

    private val _contacts = MutableLiveData<List<ContactInfo>>()
    val contacts: LiveData<List<ContactInfo>> = _contacts

    init {
        loadContacts()
    }

    fun selectContact(contact: ContactInfo) {
        _contacts.value = _contacts.value?.map {
            if (it.id == contact.id) {
                it.copy(isSelected = !it.isSelected)
            } else {
                it.copy(isSelected = false)
            }
        }
    }

    fun addContact(user: UserEntity) {
        userDataBase.addUser(user)
        loadContacts()
    }

    fun askToRemoveContact(contact: ContactInfo) {
        _showRemoveContactConfirmation.value = contact
        _contacts.value = _contacts.value?.toMutableList()?.apply { remove(contact) }
    }

    fun getUser(direction: Int) = _contacts.value?.get(direction)
    fun undoRemoveContact() {
        loadContacts()
    }

    fun removeContact(contact: ContactInfo) {
        userDataBase.deleteUser(contact.id)
    }

    fun addFakeContact() {
        userDataBase.addFakeUser()
        loadContacts()
    }

    private fun loadContacts() {
        _contacts.value = userDataBase.loadUsers()
            .map { mapper.map(it) }
    }

    fun getId(): Int {
        return _contacts.value?.last()?.id ?: 0
    }
}
