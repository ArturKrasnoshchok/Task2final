package com.example.task2.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.example.task2.contacts.ContactsViewModel
import com.example.task2.contacts.ContactsViewModelFactory
import com.example.task2.databinding.AddContactBinding
import com.example.task2.storage.models.UserEntity

class AddContactDialogFragment : DialogFragment() {
    private lateinit var binding: AddContactBinding
    private val viewModel: ContactsViewModel by activityViewModels { ContactsViewModelFactory() }

    companion object {
        val TAG: String = AddContactDialogFragment::class.java.simpleName
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = AddContactBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btLeftAddOneContact.setOnClickListener { dismiss() }
        binding.textSave.setOnClickListener {
            addContacts()
            dismiss()
        }
    }

    private fun addContacts() {
        viewModel.addContact(
            UserEntity(
                id = viewModel.getId() + 1,
                name = binding.textUsername.text.toString(),
                photo = "",
                career = binding.textCareer.text.toString(),
                email = binding.textEmail.text.toString(),
                address = binding.textAddress.text.toString(),
                birthDate = binding.textBirthDay.text.toString()
            )
        )
    }
}
