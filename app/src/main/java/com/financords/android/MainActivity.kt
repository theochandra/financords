package com.financords.android

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.financords.android.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        viewModel = ViewModelProvider(this)
                .get(MainViewModel::class.java)
        binding.viewModel = viewModel

        observeEmail()
        observeEmailError()
    }

    private fun observeEmail() {
        viewModel.email.observe(this, { email ->
            when {
                email.isBlank() -> viewModel.emailError.value = EmailErrorEvent.EMPTY
                !email.isEmail(email) -> viewModel.emailError.value = EmailErrorEvent.INVALID_FORMAT
                email.isEmail(email) -> viewModel.emailError.value = EmailErrorEvent.NONE
            }
        })
    }

    private fun observeEmailError() {
        viewModel.emailError.observe(this, { emailErrorEvent ->
            onEmailError(emailErrorEvent)
        })
    }

    private fun onEmailError(emailError: EmailErrorEvent) {
        binding.etEmail.error = getError(this, emailError)
    }

}