package com.example.task2.contacts

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.task2.contacts.list.ContactActionListener
import com.example.task2.databinding.ActivityMainBinding
import com.example.task2.storage.models.*
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar

class ContactsActivity : AppCompatActivity(), ContactActionListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: RecyclerContactsAdapter

    private val viewModel: ContactsViewModel by viewModels { ContactsViewModelFactory() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val layoutManager = LinearLayoutManager(this)
        adapter = RecyclerContactsAdapter(this)
        binding.recyclerViewMyContacts.layoutManager = layoutManager
        binding.recyclerViewMyContacts.adapter = adapter

        binding.tvAddContactsMyContacts.setOnClickListener {
            viewModel.addFakeContact()
        }

        viewModel.contacts.observe(this, adapter.differ::submitList)

        viewModel.removeContactBanner.observe(this) { contact ->
            if (contact != null) {
                showRemoveContactConfirmation(contact)
            }
        }
        binding.tvAddContactsMyContacts.setOnClickListener {
            SimpleDialogFragment().apply {
                show(
                    supportFragmentManager,
                    SimpleDialogFragment.TAG
                )
            }
        }
    }

    private fun showRemoveContactConfirmation(contact: Contact) {
        var isCancelled = false
        Snackbar.make(binding.coordinator, "Remove contact?", Snackbar.LENGTH_SHORT)
            .apply {
                setAction("Cancel") {
                    isCancelled = true
                    viewModel.undoRemoveContact()
                    showToast("Cancelled")
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

    override fun onDeleteContact(contact: Contact) {
        viewModel.askToRemoveContact(contact)
    }

    override fun onSelectContact(contact: Contact) {
        viewModel.selectContact(contact)
    }

    fun getLastId(): Int {
        return viewModel.getId() + 1
    }

}
