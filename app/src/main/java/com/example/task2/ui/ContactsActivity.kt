package com.example.task2.ui

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.task2.R
import com.example.task2.contacts.ContactsViewModel
import com.example.task2.contacts.ContactsViewModelFactory
import com.example.task2.contacts.recycler.ContactActionListener
import com.example.task2.contacts.recycler.RecyclerContactsAdapter
import com.example.task2.databinding.ActivityMainBinding
import com.example.task2.storage.models.Contact
import com.example.task2.storage.models.UserEntity
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar

class ContactsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val recyclerContactsAdapter: RecyclerContactsAdapter by lazy {
        RecyclerContactsAdapter(actionListener = object : ContactActionListener {
            override fun onDeleteContact(contact: Contact) {
                viewModel.askToRemoveContact(contact)
            }

            override fun onSelectContact(contact: Contact) {
                viewModel.selectContact(contact)
            }
        })
    }

    private val viewModel: ContactsViewModel by viewModels { ContactsViewModelFactory() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setObservers()
        initListeners()

        binding.recyclerViewMyContacts.apply {
            layoutManager = LinearLayoutManager(this@ContactsActivity)
            adapter = recyclerContactsAdapter
        }

    }

    private fun initListeners() {
        binding.tvAddContactsMyContacts.setOnClickListener {
            AddContactDialogFragment().apply {
                show(
                    supportFragmentManager,
                    AddContactDialogFragment.TAG
                )
            }
        }
    }

    private fun setObservers() {
        viewModel.contacts.observe(this, recyclerContactsAdapter.differ::submitList)

        viewModel.contactToRemove.observe(this) { contact ->
            if (contact != null) {
                showRemoveContactConfirmation(contact)
            }
        }
    }

    private fun showRemoveContactConfirmation(contact: Contact) {
        var isCancelled = false
        Snackbar.make(
            binding.coordinator,
            getString(R.string.Remove_contact),
            Snackbar.LENGTH_SHORT
        )
            .apply {
                setAction(getString(R.string.cancel)) {
                    isCancelled = true
                    viewModel.undoRemoveContact()
                    showToast(getString(R.string.cancelled))
                    dismiss()
                }
            }
            .addCallback(
                object : BaseTransientBottomBar.BaseCallback<Snackbar>() {
                    override fun onDismissed(transientBottomBar: Snackbar?, event: Int) {
                        if (!isCancelled) {
                            viewModel.removeContact(contact)
                        }
                    }
                }
            )
            .show()
    }

    private fun showToast(text: String) {
        Toast.makeText(applicationContext, text, Toast.LENGTH_SHORT).show()
    }

    fun addUser(user: UserEntity) {
        viewModel.addContact(user)
    }

    fun getLastId(): Int {
        return viewModel.getId() + 1
    }

}
