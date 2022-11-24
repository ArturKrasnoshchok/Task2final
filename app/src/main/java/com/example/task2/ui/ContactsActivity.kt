package com.example.task2.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.task2.databinding.ActivityMainBinding

class ContactsActivity() : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}
