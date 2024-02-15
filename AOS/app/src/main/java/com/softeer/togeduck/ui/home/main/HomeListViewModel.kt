package com.softeer.togeduck.ui.home.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
class HomeListViewModel : ViewModel() {
    private var _size  = MutableLiveData("")
    val size: LiveData<String> = _size
    fun setItemSize(itemCount:String){
        _size.value = itemCount
    }
}

