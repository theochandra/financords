package com.financords.android.singleliveevent

import androidx.annotation.MainThread
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer

class ActionLiveData<T> : MutableLiveData<T>() {

    @MainThread
    override fun observe(owner: LifecycleOwner, observer: Observer<in T>) {

        // being strict about the observer numbers is up to you
        // i thought it made sense to only allow one to handle the event
        if (hasObservers()) {
            throw Throwable("Only one observer at a time may subscribe to an ActionLiveData")
        }

        super.observe(owner, Observer { data ->
            // we ignore any null values and early return
            if (data == null) return@Observer
            observer.onChanged(data)
            // we set the value to null straight after emitting the change to the observer
            value = null
            // this means that the state of the data will always be null / non existent
            // it will only be available to the observer in its callback and since we do not emit null values
            // the observer never receives a null value and any observers resuming do not receive the last event.
            // therefore it only emits to the observer the single action so you are free to show the messages over and over again
            // or launch an activity / dialog or anything that should only happen once per action / click.
        })
    }

    // just a nicely named method that wraps setting the value
    @MainThread
    fun sendAction(data: T) {
        value = data
    }

}