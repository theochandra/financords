package com.financords.android.singleliveevent

import androidx.lifecycle.ViewModel

class SingleLiveEventViewModel : ViewModel() {

//    val snackBarAction = ActionLiveData<SnackBarMessage>()
//
//    fun buttonClicked(message: String) {
//        snackBarAction.sendAction(SnackBarMessage(message))
//    }

    private val _uploadAction = SingleLiveEvent<SnackBarMessage>()
    val uploadAction: SingleLiveEvent<SnackBarMessage>
        get() = _uploadAction

    fun onActionUpload(message: String) {
        _uploadAction.value = SnackBarMessage(message)
    }

}