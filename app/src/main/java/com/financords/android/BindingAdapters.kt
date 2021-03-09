package com.financords.android

import android.view.View
import android.widget.CheckBox
import android.widget.EditText
import android.widget.ScrollView
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener
import com.google.android.material.textfield.TextInputLayout

@BindingAdapter("error")
fun setError(textInputLayout: TextInputLayout, resId: Int?) {
    textInputLayout.error = resId?.let {
        textInputLayout.context.getString(it)
    }
}

@BindingAdapter("error")
fun setError(editText: EditText, resId: Int?) {
    editText.error = resId?.let {
        editText.context.getString(it)
    }
}

//@InverseBindingAdapter(attribute = "checked")
//fun getChecked(checkBox: CheckBox): Boolean {
//    return checkBox.isChecked
//}

@InverseBindingAdapter(attribute = "scrollY")
fun getScrollY(scrollView: ScrollView): Int {
    return scrollView.scrollY
}

@BindingAdapter(
    value = ["scrollListener", "scrollYAttrChanged"],
    requireAll = false
)
fun setScrollListeners(
    scrollView: ScrollView,
    scrollListener: ScrollView.OnScrollChangeListener?,
    inverseBindingListener: InverseBindingListener?
) {
    val newListener = if (inverseBindingListener == null) {
        scrollListener
    } else {
        object : ScrollView.OnScrollChangeListener() {
            override fun onScrollChange(v: View, scrollX: Int, scrollY: Int, oldX: Int, oldY: Int) {
                scrollListener?.onScrollChange(v, scrollX, scrollY, oldX, oldY)
                inverseBindingListener.onChange()
            }
        }
    }
    scrollView.setOnScrollChangeListener(newListener)

    /**
     * https://www.bignerdranch.com/blog/two-way-data-binding-on-android-observing-your-view-with-xml/
     */
}