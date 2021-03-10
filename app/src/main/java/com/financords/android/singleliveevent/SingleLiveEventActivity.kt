package com.financords.android.singleliveevent

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.financords.android.R
import com.financords.android.databinding.ActivitySingleLiveEventBinding
import com.google.android.material.snackbar.Snackbar

class SingleLiveEventActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySingleLiveEventBinding
    private lateinit var viewModel: SingleLiveEventViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil
            .setContentView(this, R.layout.activity_single_live_event)
        viewModel = ViewModelProvider(this)
            .get(SingleLiveEventViewModel::class.java)
        binding.viewModel = viewModel

//        observeSnackBarAction()
//
//        binding.btnShowSnackBar.setOnClickListener {
//            // let the view model know something happened.
//            viewModel.buttonClicked("Looks like something awesome just happened!")
//        }

        observeActionUpload()
        binding.btnShowSnackBar.setOnClickListener {
            viewModel.onActionUpload("Uploading...")
        }
    }

//    private fun observeSnackBarAction() {
//        // observe the snack bar action from the view model
//        // and show a snack bar whe a new action comes in
//        viewModel.snackBarAction.observe(this, {
//            Snackbar.make(binding.coordinatorLayout, it.text, Snackbar.LENGTH_LONG).show()
//        })
//    }

    private fun observeActionUpload() {
        viewModel.uploadAction.observe(this, {
            Snackbar.make(binding.coordinatorLayout, it.text, Snackbar.LENGTH_LONG).show()
        })
    }

}