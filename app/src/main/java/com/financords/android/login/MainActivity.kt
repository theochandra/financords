package com.financords.android.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.financords.android.R
import com.financords.android.databinding.ActivityMainBinding
import com.financords.android.legal.LegalActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil
                .setContentView(this, R.layout.activity_main)
        viewModel = ViewModelProvider(this)
                .get(MainViewModel::class.java)
        binding.viewModel = viewModel

        observeButtonClicked()
//        observeCheckBoxChecked()
    }

    private fun observeButtonClicked() {
        viewModel.buttonClicked.observe(this, {
            Toast.makeText(this,
                    "username: ${it.username} \n " +
                            "password: ${it.password} \n" +
                            "is checked: ${it.checked}",
                    Toast.LENGTH_LONG).show()

            val intent = LegalActivity.newIntent(this)
            startActivity(intent)
        })
    }

    private fun observeCheckBoxChecked() {
        viewModel.checkBoxChecked.observe(this, {
            Toast.makeText(this,
                    "is checked: $it",
                    Toast.LENGTH_LONG).show()
        })
    }

}