package com.example.task2.contacts.list

import com.example.task2.contacts.Contact

interface ContactActionListener {

    fun onDeleteContact(contact: Contact)

    fun onSelectContact(contact: Contact)
}
