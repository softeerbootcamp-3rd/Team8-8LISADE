package com.softeer.togeduck.ui.reserve_status.reserve_detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.softeer.togeduck.data.model.reserve_status.reserve_detail.SeatChartModel
import com.softeer.togeduck.data.repository.ReserveStatusRepository
import com.softeer.togeduck.utils.DATA_LOAD_ERROR_MESSAGE
import com.softeer.togeduck.utils.recordErrLog
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SeatChartViewModel @Inject constructor(private val reserveStatusRepository: ReserveStatusRepository) :
    ViewModel() {
    private val tag = this.javaClass.simpleName
    var routeId: Int = 0

    private var _errMessage = MutableLiveData<String>()
    val errMessage: LiveData<String> = _errMessage

    private var _seatChart = MutableLiveData<SeatChartModel>()
    val seatChart: LiveData<SeatChartModel> = _seatChart

    fun loadSeatChartData() {
        viewModelScope.launch {
            reserveStatusRepository.getSeatChart(routeId).onSuccess {
                _seatChart.value = it
            }.onFailure {
                recordErrLog(tag, it.message!!)
                _errMessage.value = DATA_LOAD_ERROR_MESSAGE
            }
        }
    }

}