package com.example.task2.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.task2.R
import com.example.task2.contacts.ContactsViewModel
import com.example.task2.contacts.ContactsViewModelFactory
import com.example.task2.contacts.recycler.ContactActionListener
import com.example.task2.contacts.recycler.RecyclerContactsAdapter
import com.example.task2.databinding.FragmentContactsBinding
import com.example.task2.storage.models.Contact
import com.example.task2.storage.models.UserEntity
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar


class FragmentContacts : Fragment() {
    private lateinit var binding: FragmentContactsBinding

    private val viewModel: ContactsViewModel by activityViewModels { ContactsViewModelFactory() }

    private val recyclerContactsAdapter: RecyclerContactsAdapter by lazy {
        RecyclerContactsAdapter(actionListener = object : ContactActionListener {
            override fun onDeleteContact(contact: Contact) {
                viewModel.askToRemoveContact(contact)
            }

            override fun onSelectContact(contact: Contact) {
                viewModel.selectContact(contact)
                findNavController().navigate(FragmentContactsDirections.actionFragmentContactsToFragmentProfile(contact))
            }
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentContactsBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recyclerView: RecyclerView = binding.recyclerViewMyContacts.apply { adapter = recyclerContactsAdapter }
        recyclerView.layoutManager = LinearLayoutManager(requireActivity())
        initListeners()
        setObservers()

    }

    private fun initListeners() {
        binding.tvAddContactsMyContacts.setOnClickListener {
            findNavController().navigate(R.id.addContactDialogFragment)
            navOptions {
                anim {
                    enter = androidx.navigation.ui.R.anim.nav_default_enter_anim
                    popEnter = androidx.navigation.ui.R.anim.nav_default_pop_enter_anim
                    popExit = androidx.navigation.ui.R.anim.nav_default_pop_exit_anim
                    exit = androidx.navigation.ui.R.anim.nav_default_exit_anim
                }
            }
        }
    }

    private fun setObservers() {
        viewModel.contacts.observe(viewLifecycleOwner, recyclerContactsAdapter.differ::submitList)

        viewModel.contactToRemove.observe(viewLifecycleOwner) { contact ->
            if (contact != null) {
                showRemoveContactConfirmation(contact)
            }
        }
    }

    private fun showRemoveContactConfirmation(contact: Contact) {
        var isCancelled = false
        Snackbar.make(
            binding.coordinator, getString(R.string.Remove_contact), Snackbar.LENGTH_SHORT
        ).apply {
            setAction(getString(R.string.cancel)) {
                isCancelled = true
                viewModel.undoRemoveContact()
                showToast(getString(R.string.cancelled))
                dismiss()
            }
        }.addCallback(object : BaseTransientBottomBar.BaseCallback<Snackbar>() {
            override fun onDismissed(transientBottomBar: Snackbar?, event: Int) {
                if (!isCancelled) {
                    viewModel.removeContact(contact)
                }
            }
        }).show()
    }

    private fun showToast(text: String) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
    }

    fun addUser(user: UserEntity) {
        viewModel.addContact(user)
    }

    fun getLastId(): Int {
        return viewModel.getId() + 1
    }
}