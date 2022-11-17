package com.example.task2.contacts.list

import com.example.task2.contacts.ContactInfo

interface ContactActionListener {

    fun onDeleteContact(contact: ContactInfo)

    fun onSelectContact(contact: ContactInfo)
}
