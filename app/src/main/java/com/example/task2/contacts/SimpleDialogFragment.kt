package com.example.task2.contacts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.task2.databinding.AddContactBinding
import com.example.task2.storage.models.UserEntity

class SimpleDialogFragment : DialogFragment() {
    private lateinit var binding: AddContactBinding


    companion object {
        val TAG: String = SimpleDialogFragment::class.java.simpleName

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

    // todo handle result listener from dialog in activity
    private fun addContacts() {
        (activity as ContactsActivity).addUser(
            UserEntity(
                id = (activity as ContactsActivity).getLastId(),
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
