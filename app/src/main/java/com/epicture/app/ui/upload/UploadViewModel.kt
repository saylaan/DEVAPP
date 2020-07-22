package com.epicture.app.ui.upload

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class UploadViewModel : ViewModel() {

    private val _text1 = MutableLiveData<String>().apply {
        value = "Use camera"
    }

    private val _text2 = MutableLiveData<String>().apply {
        value = "Upload"
    }
    val text: LiveData<String> = _text1

    val textUpload: LiveData<String> = _text2

}