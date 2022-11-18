package com.example.task2.contacts.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.task2.R
import com.example.task2.contacts.Contact
import com.example.task2.databinding.ItemContactBinding
import com.example.task2.extension.loadCirclePicture


// todo use ListAdapter<> instead of RecyclerView.Adapter<>
class RecyclerContactsAdapter(
    private val actionListener: ContactActionListener,
) : RecyclerView.Adapter<RecyclerContactsAdapter.RecyclerContactsHolder>() {

    private val differCallback = object : DiffUtil.ItemCallback<Contact>() {
        override fun areItemsTheSame(oldItem: Contact, newItem: Contact): Boolean {
            return ((oldItem.id == newItem.id) && oldItem.name == newItem.name)
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

    override fun getItemCount(): Int = differ.currentList.size


    inner class RecyclerContactsHolder(
        private val binding: ItemContactBinding,
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(contact: Contact) {
            with(binding) {
                with(contact) {

                    root.isSelected = isSelected

                    tvNameMyContacts.text = name
                    tvCareerMyContacts.text = career

                    initListeners(contact)

                    bindPhoto(this)
                }
            }
        }

        private fun bindPhoto(
            contact: Contact,
        ) {
            binding.imageViewPhoto.run {
                if (contact.photoUri.isNotBlank()) {
                    loadCirclePicture(contact.photoUri)
                } else {
                    setImageResource(R.drawable.ic_user_avatar)
                }
            }
        }

        private fun initListeners(contact: Contact) {
            binding.run {
                root.setOnClickListener {
                    actionListener.onSelectContact(contact)
                }
                buttonBasket.setOnClickListener {
                    actionListener.onDeleteContact(contact)
                }
            }
        }
    }

}
