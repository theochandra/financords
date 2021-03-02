package com.financords.android

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
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
        if (savedInstanceState == null) {
            viewModel.init()
        }
        binding.viewModel = viewModel

        setupButtonClick()
    }

    private fun setupButtonClick() {
        viewModel.getLoginFields().observe(this, {
            Toast.makeText(
                    this@MainActivity,
                    "Email " + it.email
                            + ", Password " + it.password,
                    Toast.LENGTH_SHORT
            ).show()
        })
    }

}