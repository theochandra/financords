package com.financords.android.model

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.lifecycle.MutableLiveData
import com.financords.android.BR
import com.financords.android.R

class LoginForm : BaseObservable() {

    private val fields = LoginFields("", "")
    private val errors = LoginErrorFields(0, 0)
    private val buttonClick = MutableLiveData<LoginFields>()

    @Bindable
    fun isValid(): Boolean {
        var valid = isEmailValid(false)
        valid = isPasswordValid(false) && valid
        notifyPropertyChanged(BR.emailError)
        notifyPropertyChanged(BR.passwordError)
        return valid
    }

    fun isEmailValid(setMessage: Boolean): Boolean {
        // Minimum a@b.c
        val email = fields.email
        if (email.length > 5) {
            val indexOfAt = email.indexOf("@")
            val indexOfDot = email.lastIndexOf(".")
            return if (indexOfAt in 1 until indexOfDot && indexOfDot < email.length - 1) {
                errors.email = null
                notifyPropertyChanged(BR.valid)
                true
            } else {
                if (setMessage) {
                    errors.email = R.string.error_format_invalid
                    notifyPropertyChanged(BR.valid)
                }
                false
            }
        }
        if (setMessage) {
            errors.email = R.string.error_too_short
            notifyPropertyChanged(BR.valid)
        }

        return false
    }

    fun isPasswordValid(setMessage: Boolean): Boolean {
        val password: String = fields.password
        return if (password.length > 5) {
            errors.password = null
            notifyPropertyChanged(BR.valid)
            true
        } else {
            if (setMessage) {
                errors.password = R.string.error_too_short
                notifyPropertyChanged(BR.valid)
            }
            false
        }
    }

    fun onClick() {
        if (isValid()) {
            buttonClick.value = fields
        }
    }

    fun getLoginFields(): MutableLiveData<LoginFields> {
        return buttonClick
    }

    fun getFields(): LoginFields {
        return fields
    }

    @Bindable
    fun getEmailError(): Int? {
        return errors.email
    }

    @Bindable
    fun getPasswordError(): Int? {
        return errors.password
    }

}