package com.financords.android

import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {

    val emailError = MutableLiveData<EmailErrorEvent>()

    val email = MutableLiveData<String>()

    val input = ObservableField<String>()

}