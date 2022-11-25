package com.example.task2.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.task2.R
import com.example.task2.databinding.FragmentProfileBinding


class FragmentProfile : Fragment() {
    private lateinit var binding: FragmentProfileBinding
    private val args: FragmentProfileArgs by navArgs()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentProfileBinding.inflate(inflater, container, false)
           return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
      initialize()
    }

    private fun initialize() {
        binding.buttonArrowLeftProfile.setOnClickListener {
            findNavController().navigate(R.id.fragmentContacts)
        }
        val contact = args.userProfile
        with(binding) {
            tvUserName.text = contact.name
            tvUserProfession.text = contact.career
            tvUserAddress.text = contact.address
        }

    }

}
