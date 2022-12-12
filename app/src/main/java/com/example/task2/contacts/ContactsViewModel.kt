package com.example.task2.contacts

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.NavDirections
import com.example.task2.extension.OneTimeEvent
import com.example.task2.storage.UserDataBase
import com.example.task2.storage.models.Contact
import com.example.task2.storage.models.UserEntity
import com.example.task2.storage.models.UserToContactMapper
import com.example.task2.ui.FragmentContactsDirections
import com.example.task2.util.ContactSelector
import com.example.task2.util.selectedManyContacts


// TODO: use koin or Hilt for injecting dependencies 👇
//I will implement when there are ideas on how to do it
class ContactsViewModel(
    private val userDataBase: UserDataBase,
    private val mapper: UserToContactMapper,
) : ViewModel(), ContactSelector {

    private val _contactToRemove = MutableLiveData<Contact?>()
    val contactToRemove: LiveData<Contact?> = _contactToRemove

    private val _contacts = MutableLiveData<List<Contact>>()
    val contacts: LiveData<List<Contact>> = _contacts

    private val _navigation = MutableLiveData<OneTimeEvent<NavDirections>>()
    val navigation: LiveData<OneTimeEvent<NavDirections>> = _navigation

    init {
        loadContacts()
    }

    fun selectContact(contact: Contact) {
        _contacts.value = _contacts.value?.map {
            if (it.id == contact.id) {
                it.copy(isSelected = !it.isSelected)
            } else {
                it.copy(isSelected = it.isSelected)
            }
        }
    }

    private fun getContactList(): MutableList<Contact> {
        return _contacts.value!!.toMutableList()
    }

    fun deleteAllSelectedContacts() {
        selectedManyContacts = _contacts.value!!.toMutableList()
        for (i in 0 until _contacts.value!!.size) {
            if (selectedManyContacts[i].isSelected) {
                removeContact(selectedManyContacts[i])
            }
        }
        selectedManyContacts.clear()
        loadContacts()
    }


    fun navigateToDetails(contact: Contact) {
        _navigation.value = OneTimeEvent(FragmentContactsDirections.actionFragmentContactsToFragmentProfile(contact))
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

    override fun isAnyContactSelected(): Boolean {
        var selectedContacts = getContactList()
        var tempCounter = 0

        for (i in 0 until selectedContacts.size) {
            if (selectedContacts[i].isSelected) {
                tempCounter++
            }
        }
        return if (tempCounter == 0) {
            return false
        } else true
    }
}

