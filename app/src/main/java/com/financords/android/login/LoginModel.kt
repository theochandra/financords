package com.financords.android.login

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.databinding.ObservableField
import com.financords.android.BR
import com.financords.android.R

class LoginModel : BaseObservable() {

    var password = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.valid)
        }

    var username = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.valid)
        }

    var checked = false
        set(value) {
            field = value
            notifyPropertyChanged(BR.valid)
        }

    val passwordError = ObservableField<Int>()
    val usernameError = ObservableField<Int>()

    // bindable annotation will make this method or variable
    // can be notified by other variable changes
    @Bindable
    fun isValid(): Boolean {
        var valid = isUsernameValid()
        valid = isPasswordValid() && valid
//        valid = isChecked() && valid
        return valid
    }

    private fun isPasswordValid(): Boolean {
        return when {
            password.isEmpty() -> {
//                passwordError.set(R.string.error_format_invalid)
                false
            }
            password.length < 8 -> {
                passwordError.set(R.string.error_too_short)
                false
            }
            else -> {
                passwordError.set(null)
                true
            }
        }
    }

    private fun isUsernameValid(): Boolean {
        return when {
            username.isEmpty() -> {
//                usernameError.set(R.string.error_format_invalid)
                false
            }
            username.length < 6 -> {
                usernameError.set(R.string.error_too_short)
                false
            }
            else -> {
                usernameError.set(null)
                true
            }
        }
    }

    private fun isChecked(): Boolean {
        return checked
    }

}