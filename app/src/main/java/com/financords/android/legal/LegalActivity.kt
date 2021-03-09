package com.financords.android.legal

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.financords.android.R
import com.financords.android.databinding.ActivityLegalBinding

class LegalActivity : AppCompatActivity() {

    companion object {
        @JvmStatic
        fun newIntent(context: Context): Intent {
            return Intent(context, LegalActivity::class.java)
        }
    }

    private lateinit var binding: ActivityLegalBinding
    private lateinit var viewModel: LegalViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_legal)
        viewModel = ViewModelProvider(this)
            .get(LegalViewModel::class.java)
        binding.viewModel = viewModel
    }

}