package com.example.task2.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.ListFragment
import androidx.navigation.findNavController
import com.example.task2.R
import com.example.task2.contacts.recycler.AdapterContactsActivity
import com.example.task2.databinding.ActivityMainBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import com.google.android.material.tabs.TabLayoutMediator

class ContactsActivity : AppCompatActivity() {

    /**
     * Expand the intent with 2 additional parameters: firstname and lastname
     */
    companion object {
        private const val FIRST_NAME_ARG = "FIRST_NAME_ARG"
        private const val LAST_NAME_ARG = "LAST_NAME_ARG"


        fun getIntent(context: Context, firstName: String, lasName: String): Intent {
            return Intent(context, ContactsActivity::class.java).apply {
                putExtra(FIRST_NAME_ARG, firstName)
                putExtra(LAST_NAME_ARG, lasName)
            }
        }
    }


    private val firstName: String
        get() = intent.getStringExtra(FIRST_NAME_ARG)!!
    private val lastName: String
        get() = intent.getStringExtra(LAST_NAME_ARG)!!

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val adapter = AdapterContactsActivity(this)
        binding.fragmentContainerView.adapter = adapter
        TabLayoutMediator(binding.tanLayoutMain, binding.fragmentContainerView) { tab, position ->
            when (position) {
                0 -> tab.text = "My profile"
                1 -> tab.text = "My contacts"
            }
        }.attach()
//        binding.tanLayoutMain.addOnTabSelectedListener(object : OnTabSelectedListener {
//            override fun onTabSelected(tab: TabLayout.Tab?) {
//
//                if (tab?.position == 0)
//                    findNavController(R.id.fragmentContainerView).navigate(R.id.fragmentOwnerProfile)
//
//                if (tab?.position == 1)
//                    findNavController(R.id.fragmentContainerView).navigate(R.id.fragmentContacts)
//            }
//
//            override fun onTabUnselected(tab: TabLayout.Tab?) {
//
//            }
//
//            override fun onTabReselected(tab: TabLayout.Tab?) {
//
//            }
//        })
    }

}
