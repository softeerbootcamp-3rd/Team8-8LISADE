package com.softeer.togeduck.data.remote.datasource

import com.softeer.togeduck.data.dto.response.reserve_status.reserve_detail.ReserveStatusDetailResponse
import com.softeer.togeduck.data.dto.response.reserve_status.ReserveStatusResponse
import com.softeer.togeduck.data.dto.response.reserve_status.reserve_detail.MobileTicketResponse
import com.softeer.togeduck.data.dto.response.reserve_status.reserve_detail.SeatChartResponse
import com.softeer.togeduck.data.remote.service.ReserveStatusService
import retrofit2.Response
import javax.inject.Inject

class ReserveStatusRemoteDataSource @Inject constructor(
    private val reserveStatusService: ReserveStatusService
) {
    suspend fun getReserveStatusList(
        page: Int,
        size: Int,
    ): ReserveStatusResponse {
        return reserveStatusService.getReserveStatusList(page, size)
    }

    suspend fun getReserveStatusDetail(
        routeId: Int,
    ): ReserveStatusDetailResponse {
        return reserveStatusService.getReserveStatusDetail(routeId)
    }

    suspend fun getMobileTicket(
        routeId: Int,
    ): MobileTicketResponse {
        return reserveStatusService.getMobileTicket(routeId)
    }

    suspend fun getSeatChart(
        routeId: Int,
    ): SeatChartResponse {
        return reserveStatusService.getSeatChart(routeId)
    }
}