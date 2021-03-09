package com.financords.android.legal

import android.view.View
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.financords.android.BR

class LegalModel : BaseObservable() {

    @Bindable
    var scrollY = 0
        set(value) {
            field = value
            notifyPropertyChanged(BR.scrollY)
            notifyPropertyChanged(BR.scrollToTopFabVisibility)
        }

    @get:Bindable
    val scrollToTopFabVisibility: Int
        get() = if (scrollY == 0) View.GONE else View.VISIBLE

    fun scrollToTop() {
        scrollY = 0
    }

}