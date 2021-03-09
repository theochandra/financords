package com.financords.android

import androidx.annotation.StringRes

interface ErrorEvent {

    @StringRes
    fun getErrorResource(): Int

}