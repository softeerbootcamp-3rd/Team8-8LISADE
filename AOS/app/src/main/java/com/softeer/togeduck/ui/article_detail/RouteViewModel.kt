package com.softeer.togeduck.ui.article_detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class RouteViewModel:ViewModel() {
    private var _size  = MutableLiveData("0")
    val size: LiveData<String> = _size
    fun setItemSize(itemCount:String){
        _size.value = itemCount
    }
}