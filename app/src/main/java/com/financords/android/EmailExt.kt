package com.financords.android

import android.content.Context

fun getError(from: Context, errorEvent: ErrorEvent) = if (errorEvent.getErrorResource() == 0) {
    null
} else {
    from.getString(errorEvent.getErrorResource())
}

fun String.isEmail(email: String): Boolean {
    return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
}