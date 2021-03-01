package com.financords.android

import android.view.View.OnFocusChangeListener
import android.widget.EditText
import androidx.databinding.BindingAdapter
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {

    private val loginFields = LoginFields()
    private var onFocusEmail: OnFocusChangeListener? = null
    private var onFocusPassword: OnFocusChangeListener? = null
    private val buttonClick = MutableLiveData<LoginFields>()

    fun init() {
        onFocusEmail = OnFocusChangeListener { view, focused ->
            val editText = view as EditText
            if (editText.text.isNotEmpty() && !focused) {
                loginFields.isEmailValid(true)
            }
        }

        onFocusPassword = OnFocusChangeListener { view, focused ->
            val editText = view as EditText
            if (editText.text.isNotEmpty() && !focused) {
                loginFields.isPasswordValid(true)
            }
        }
    }

    fun getLogin(): LoginFields? {
        return loginFields
    }

    fun getEmailOnFocusChangeListener(): OnFocusChangeListener? {
        return onFocusEmail
    }

    fun getPasswordOnFocusChangeListener(): OnFocusChangeListener? {
        return onFocusPassword
    }

    fun onButtonClick() {
        if (loginFields.isValid()) {
            buttonClick.value = loginFields
        }
    }

    fun getButtonClick(): MutableLiveData<LoginFields> {
        return buttonClick
    }

    companion object {
        @JvmStatic
        @BindingAdapter("error")
        fun setError(editText: EditText, strOrResId: Int?) {
            if (strOrResId is Int) {
                editText.error = editText.context.getString((strOrResId as Int?)!!)
            } else {
                editText.error = strOrResId as String?
            }
        }

        @JvmStatic
        @BindingAdapter("onFocus")
        fun bindFocusChange(editText: EditText, onFocusChangeListener: OnFocusChangeListener?) {
            if (editText.onFocusChangeListener == null) {
                editText.onFocusChangeListener = onFocusChangeListener
            }
        }
    }

}