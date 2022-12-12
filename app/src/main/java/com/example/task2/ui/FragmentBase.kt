package com.example.task2.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.example.task2.contacts.recycler.AdapterContactsActivity
import com.example.task2.databinding.FragmentBaseBinding
import com.google.android.material.tabs.TabLayoutMediator

class FragmentBase : Fragment() {

    private lateinit var binding: FragmentBaseBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBaseBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = AdapterContactsActivity(activity as AppCompatActivity)
        binding.fragmentPager2.adapter = adapter
        TabLayoutMediator(binding.tanLayoutMain, binding.fragmentPager2) { tab, position ->
            when (position) {
                0 -> tab.text = "My profile"
                1 -> tab.text = "My contacts"
            }
        }.attach()
    }
}