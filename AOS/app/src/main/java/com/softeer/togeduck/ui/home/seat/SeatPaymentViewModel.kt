package com.softeer.togeduck.ui.home.seat

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.softeer.togeduck.data.model.home.seat.MySeatModel
import com.softeer.togeduck.data.model.home.seat.SeatPaymentModel
import com.softeer.togeduck.data.repository.SeatRepository
import com.softeer.togeduck.utils.DATA_LOAD_ERROR_MESSAGE
import com.softeer.togeduck.utils.recordErrLog
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SeatPaymentViewModel @Inject constructor(
    private val seatRepository: SeatRepository
) :
    ViewModel() {
    private val tag = this.javaClass.simpleName
    var routeId: Int = -1
    var selectedNum: Int = -1

    private var _errMessage = MutableLiveData<String>()
    val errMessage: LiveData<String> = _errMessage

    private var _seatPayment = MutableLiveData<SeatPaymentModel>()
    val seatPayment: LiveData<SeatPaymentModel> = _seatPayment

    fun loadSeatPaymentData() {
        viewModelScope.launch {
            seatRepository.getSeatPayment(routeId, selectedNum).onSuccess {
                _seatPayment.value = it
            }.onFailure {
                recordErrLog(tag, it.message!!)
                _errMessage.value = DATA_LOAD_ERROR_MESSAGE
            }
        }
    }

    fun setMySeatData() {
        viewModelScope.launch {
            seatRepository.setMySeat(routeId, MySeatModel(selectedNum.toString()))
        }
    }


}