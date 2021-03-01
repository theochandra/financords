package com.financords.android

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.databinding.ObservableField

class LoginFields : BaseObservable() {

    private var email: String = ""
    private var password: String = ""

    val emailError = ObservableField<Int>()
    val passwordError = ObservableField<Int>()

    fun getEmail(): String {
        return email;
    }

    fun setEmail(email: String) {
        this.email = email;
        notifyPropertyChanged(BR.valid)
    }

    fun getPassword(): String {
        return password
    }

    fun setPassword(password: String) {
        this.password = password
        notifyPropertyChanged(BR.valid)
    }

    @Bindable
    fun isValid(): Boolean {
        var valid = isEmailValid(false)
        valid = isPasswordValid(false) && valid
        return valid
    }

    fun isEmailValid(setMessage: Boolean): Boolean {
        if (email != null && email.length > 5) {
            val indexOfAt = email.indexOf("@")
            val indexOfDot = email.lastIndexOf(".")
            if (indexOfAt > 0 && indexOfDot > indexOfAt && indexOfDot < email.length - 1) {
                emailError.set(null)
                return true
            } else {
                if (setMessage)
                    emailError.set(R.string.error_format_invalid)
                return false
            }
        }
        if (setMessage)
            emailError.set(R.string.error_too_short)
        return false
    }

    fun isPasswordValid(setMessage: Boolean): Boolean {
        return if (password != null && password.length > 5) {
            passwordError.set(null)
            true
        } else {
            if (setMessage) passwordError.set(R.string.error_too_short)
            false
        }
    }

}