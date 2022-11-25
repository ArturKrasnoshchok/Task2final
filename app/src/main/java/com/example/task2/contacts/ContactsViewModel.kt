package com.example.task2.contacts

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.task2.storage.UserDataBase
import com.example.task2.storage.models.Contact
import com.example.task2.storage.models.UserEntity
import com.example.task2.storage.models.UserToContactMapper


// TODO: use koin or Hilt for injecting dependencies 👇
//I will implement when there are ideas on how to do it
class ContactsViewModel(
    private val userDataBase: UserDataBase,
    private val mapper: UserToContactMapper,
) : ViewModel() {

    private val _contactToRemove = MutableLiveData<Contact?>()
    val contactToRemove: LiveData<Contact?> = _contactToRemove

    private val _contacts = MutableLiveData<List<Contact>>()
    val contacts: LiveData<List<Contact>> = _contacts

    init {
        loadContacts()
    }

    // TODO: дописав ретьорн, хочу вертати контакт 
    fun selectContact(contact: Contact): Contact {
        _contacts.value = _contacts.value?.map {
            if (it.id == contact.id) {
                it.copy(isSelected = !it.isSelected)
            } else {
                it.copy(isSelected = false)
            }
        }
        return contact
    }

    fun addContact(user: UserEntity) {
        userDataBase.addUser(user)
        loadContacts()
    }

    fun askToRemoveContact(contact: Contact) {
        _contactToRemove.value = contact
        _contacts.value = _contacts.value?.toMutableList()?.apply { remove(contact) }
    }

    fun getUser(direction: Int) = _contacts.value?.get(direction)
    fun undoRemoveContact() {
        loadContacts()
    }

    fun removeContact(contact: Contact) {
        userDataBase.deleteUser(contact.id)
    }

    private fun loadContacts() {
        _contacts.value = userDataBase.loadUsers()
            .map { mapper.map(it) }
    }

    fun getId(): Int {
        return _contacts.value?.last()?.id ?: 0
    }
}
