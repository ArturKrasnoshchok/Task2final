package com.example.task2.contacts.recycler

import com.example.task2.storage.models.Contact

interface ContactActionListener {

    fun onDeleteContact(contact: Contact)

    fun onSelectContact(contact: Contact)
}
