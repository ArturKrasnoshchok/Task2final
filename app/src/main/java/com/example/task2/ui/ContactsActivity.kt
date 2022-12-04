package com.example.task2.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.task2.databinding.ActivityMainBinding

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
    }

}
