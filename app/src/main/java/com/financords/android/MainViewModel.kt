package com.financords.android

import android.view.View.OnFocusChangeListener
import android.widget.EditText
import androidx.annotation.VisibleForTesting
import androidx.databinding.BindingAdapter
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.financords.android.model.LoginFields
import com.financords.android.model.LoginForm

class MainViewModel : ViewModel() {

    private var login: LoginForm? = null
    private var onFocusEmail: OnFocusChangeListener? = null
    private var onFocusPassword: OnFocusChangeListener? = null

    @VisibleForTesting
    fun init() {
        login = LoginForm()

        onFocusEmail = OnFocusChangeListener { view, focused ->
            val et = view as EditText
            if (et.text.isNotEmpty() && !focused) {
                login!!.isEmailValid(true)
            }
        }

        onFocusPassword = OnFocusChangeListener { view, focused ->
            val et = view as EditText
            if (et.text.isNotEmpty() && !focused) {
                login!!.isPasswordValid(true)
            }
        }
    }

    fun getLogin(): LoginForm? {
        return login
    }

    fun getEmailOnFocusChangeListener(): OnFocusChangeListener? {
        return onFocusEmail
    }

    fun getPasswordOnFocusChangeListener(): OnFocusChangeListener? {
        return onFocusPassword
    }

    fun onButtonClick() {
        login!!.onClick()
    }

    fun getLoginFields(): MutableLiveData<LoginFields> {
        return login!!.getLoginFields()
    }

    fun getForm(): LoginForm? {
        return login
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