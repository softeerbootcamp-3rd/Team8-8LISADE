package com.softeer.togeduck.ui.home.open_route

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.softeer.togeduck.data.model.home.open_route.RegionListModel
import com.softeer.togeduck.data.repository.RegionListRepository
import com.softeer.togeduck.utils.DATA_LOAD_ERROR_MESSAGE
import com.softeer.togeduck.utils.recordErrLog
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class RegionListViewModel @Inject constructor(
    private val regionListRepository: RegionListRepository
) : ViewModel() {

    private val tag = this.javaClass.simpleName.substring(0, 4)

    private var _errMessage = MutableLiveData<String>()
    val errMessage: LiveData<String> = _errMessage

    private val _selectedRegion = MutableLiveData("선택")
    val selectedRegion: LiveData<String> = _selectedRegion

    private val _selectedBusId = MutableLiveData(-1)
    val selectedBusId: LiveData<Int> = _selectedBusId

    private val _regionList = MutableLiveData<List<RegionListModel>>()
    val regionList: LiveData<List<RegionListModel>> = _regionList

    private val _isSelectCompleted = MutableLiveData(false)
    val isSelectCompleted = _isSelectCompleted

    private val _isCanOpenRoute = MutableLiveData(false)
    val isCanOpenRoute = _isCanOpenRoute


    fun setSelectedRegion(text: String) {
        _selectedRegion.value = text
    }

    fun getPopularFestival() {
        viewModelScope.launch {
            regionListRepository.getRegionList()
                .onSuccess {
                    _regionList.value = it
                }
                .onFailure {
                    recordErrLog(tag, it.message!!)
                    _errMessage.value = DATA_LOAD_ERROR_MESSAGE
                }
        }
    }

    fun selectBusId(id: Int) {
        _selectedBusId.value = id
    }

    fun reSetSelectedCompleted() {
        _isSelectCompleted.value = false
    }

    fun selectCompleted() {
        _isSelectCompleted.value = true
    }

    fun isSelectedRegionCompleted() {
        _isCanOpenRoute.value = _selectedRegion.value != "선택" && _selectedBusId.value != -1
    }

}