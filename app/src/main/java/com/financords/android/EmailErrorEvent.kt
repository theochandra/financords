package com.financords.android

import androidx.annotation.StringRes

enum class EmailErrorEvent(
    @StringRes private val resourceId: Int
) : ErrorEvent {

    NONE(0),
    EMPTY(R.string.error_too_short),
    INVALID_FORMAT(R.string.error_format_invalid);

    override fun getErrorResource(): Int = resourceId
}