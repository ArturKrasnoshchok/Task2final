package com.example.task2.storage.models

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.task2.R
import com.example.task2.contacts.Contact
import com.example.task2.contacts.list.ContactActionListener
import com.example.task2.databinding.ItemContactBinding

class RecyclerContactsAdapter(
    private val actionListener: ContactActionListener,
) : RecyclerView.Adapter<RecyclerContactsAdapter.RecyclerContactsHolder>() {

    private val differCallback = object : DiffUtil.ItemCallback<Contact>() {
        override fun areItemsTheSame(oldItem: Contact, newItem: Contact): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Contact, newItem: Contact): Boolean {
            return oldItem == newItem
        }
    }
    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerContactsHolder {
        return RecyclerContactsHolder(
            ItemContactBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(
        holder: RecyclerContactsHolder,
        position: Int,
    ) {
        holder.bind(differ.currentList[position])
    }

    inner class RecyclerContactsHolder(
        private val binding: ItemContactBinding,
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(contact: Contact) = with(binding) {
            root.isSelected = contact.isSelected

            tvNameMyContacts.text = contact.name
            tvCareerMyContacts.text = contact.career

            root.setOnClickListener {
                actionListener.onSelectContact(contact)
            }
            buttonBasket.setOnClickListener {
                actionListener.onDeleteContact(contact)
            }

            if (contact.photoUri.isNotBlank()) {
                imageViewPhoto.loadPicture(contact.photoUri)
            } else {
                imageViewPhoto.setImageResource(R.drawable.ic_user_avatar)
            }
        }

        private fun ImageView.loadPicture(image: String) {
            Glide.with(context)
                .load(image)
                .circleCrop()
                .placeholder(R.drawable.ic_user_avatar)
                .error(R.drawable.ic_user_avatar)
                .into(this)
        }
    }

    override fun getItemCount(): Int = differ.currentList.size
}
