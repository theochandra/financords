package com.financords.android.legal

import android.view.View
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.financords.android.BR

class LegalModel : BaseObservable() {

    /**
     * Depending on which view you're using and which attribute view model is subscribing to,
     * setting up two-way data binding like this can cause infinite loop.
     * What's worse is that this infinite loop won't cause your app to crash or even freeze -
     * it will silently consume CPU resources and drain the battery without any obvious problems.
     *
     * The example with scrollview doesn't exhibit this behaviour, but suppose that
     * scrollview implemented a setScrollY method like this:
     *
     * public class ScrollView extends View {
     *  public void setScrollY(int y) {
     *      mScrollY = y;
     *      if (mScrollListener != null) {
     *          mScrollListener.onScrollChanged(this, mScrollX, mScrollY, mOldScrollX, mOldScrollY);
     *      }
     *  }
     * }
     *
     * Every time that this scrollview.setScrollY method is called, your callback is also triggered.
     * Your callback is assigning the scrollY property in your LegalModel class from before.
     * For reference the scrollY setter is implemented like this*:
     *
     * Because of the call to notifyPropertyChanged(BR.scrollY), your layout's binding class
     * is going to call ScrollView.setScrollY again, which causes this entire cycle to start all over.
     * Because of the way data binding implements binding classes, though, it will wait
     * until the next frame before calling setScrollY on ScrollView.
     *
     * The solution is very simple.
     * In your LegalModel, update the scrollY setter to do nothing if the value didn't change**.
     * It's a good idea to do this on all the setters that you use with two-way data binding.
     * View implementations can change over time so updating your one of your libraries or using
     * different version of Android may cause this infinite loop behaviour without any warnings.
     * Adding this check is a simple way to guarantee that your two-way data binding won't cause
     * an infinite loop.
     */
    @Bindable
    var scrollY = 0 //* like this
        set(value) {
            if (field != value) { //** this is the solution
                field = value
                notifyPropertyChanged(BR.scrollY)
                notifyPropertyChanged(BR.scrollToTopFabVisibility)
            }
        }

    @get:Bindable
    val scrollToTopFabVisibility: Int
        get() = if (scrollY == 0) View.GONE else View.VISIBLE

    fun scrollToTop() {
        scrollY = 0
    }

}