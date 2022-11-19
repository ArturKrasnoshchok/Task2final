package com.example.task2.util

import androidx.recyclerview.widget.DiffUtil
import com.example.task2.storage.models.Contact

object UserContactsDiffUtil : DiffUtil.ItemCallback<Contact>() {
    override fun areItemsTheSame(oldItem: Contact, newItem: Contact): Boolean {
        return ((oldItem.id == newItem.id) && oldItem.name == newItem.name)
    }

    override fun areContentsTheSame(oldItem: Contact, newItem: Contact): Boolean {
        return oldItem == newItem
    }
}