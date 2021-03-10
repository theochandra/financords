package com.financords.android.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {

    val loginModel = LoginModel()

    private val _buttonClicked = MutableLiveData<LoginModel>()
    val buttonClicked: LiveData<LoginModel>
        get() = _buttonClicked

    private val _checkBoxChecked = MutableLiveData<Boolean>()
    val checkBoxChecked: LiveData<Boolean>
        get() = _checkBoxChecked

    fun onCheckBoxChecked() {
        _checkBoxChecked.postValue(loginModel.checked)
    }

    fun onButtonClicked() {
        if (loginModel.isValid()) {
            _buttonClicked.postValue(loginModel)
        }
    }

}