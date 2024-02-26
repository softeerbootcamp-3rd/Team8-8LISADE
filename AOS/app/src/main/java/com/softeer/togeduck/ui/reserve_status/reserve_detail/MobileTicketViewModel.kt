package com.softeer.togeduck.ui.reserve_status.reserve_detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.softeer.togeduck.data.model.reserve_status.reserve_detail.MobileTicketModel
import com.softeer.togeduck.data.repository.ReserveStatusRepository
import com.softeer.togeduck.utils.DATA_LOAD_ERROR_MESSAGE
import com.softeer.togeduck.utils.recordErrLog
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MobileTicketViewModel @Inject constructor(private val reserveStatusRepository: ReserveStatusRepository) :
    ViewModel() {
    private val tag = this.javaClass.simpleName
    var routeId: Int = 0

    private var _errMessage = MutableLiveData<String>()
    val errMessage: LiveData<String> = _errMessage

    private var _mobileTicket = MutableLiveData<MobileTicketModel>()
    val mobileTicket: LiveData<MobileTicketModel> = _mobileTicket

    fun loadMobileTicketData() {
        viewModelScope.launch {
            reserveStatusRepository.getMobileTicket(routeId).onSuccess {
                _mobileTicket.value = it
            }.onFailure {
                recordErrLog(tag, it.message!!)
                _errMessage.value = DATA_LOAD_ERROR_MESSAGE
            }
        }
    }
}