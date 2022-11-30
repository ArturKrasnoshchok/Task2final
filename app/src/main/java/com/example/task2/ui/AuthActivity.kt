package com.example.task2.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.task2.databinding.ActivityAuthBinding
import com.example.task2.storage.models.UserStore
import com.example.task2.util.AuthView
import java.util.*

class AuthActivity : AppCompatActivity(), AuthView {

    private lateinit var binding: ActivityAuthBinding
    private lateinit var presenter: AuthPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)
        presenter = AuthPresenter(
            this,
            this,
            UserStore(this)
        )

        binding.buttonRegister.setOnClickListener {
            presenter.sighUp(
                binding.editTextEmail.text.toString().lowercase(Locale.ROOT),
                binding.editTextPassword.text.toString().lowercase(Locale.ROOT)
            )
        }
        binding.checkBoxRememberMe.setOnCheckedChangeListener { _, isChecked ->
            presenter.rememberUser(isChecked)
        }
    }

    override fun showIncorrectEmail() {
        binding.editTextEmail.error = "Incorrect e-mail"
    }

    override fun showIncorrectPassword() {
        binding.editTextPassword.error = "Incorrect password"
    }
}