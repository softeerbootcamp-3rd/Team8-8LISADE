package com.softeer.togeduck.data.repository

import com.softeer.togeduck.data.mapper.toMobileTicketModel
import com.softeer.togeduck.data.mapper.toReserveStatusDetailModel
import com.softeer.togeduck.data.mapper.toReserveStatusModel
import com.softeer.togeduck.data.mapper.toSeatChartModel
import com.softeer.togeduck.data.model.reserve_status.ReserveStatusModel
import com.softeer.togeduck.data.model.reserve_status.reserve_detail.MobileTicketModel
import com.softeer.togeduck.data.model.reserve_status.reserve_detail.ReserveStatusDetailModel
import com.softeer.togeduck.data.model.reserve_status.reserve_detail.SeatChartModel
import com.softeer.togeduck.data.remote.datasource.ReserveStatusRemoteDataSource
import javax.inject.Inject

class ReserveStatusRepository @Inject constructor(
    private val reserveStatusRemoteDataSource: ReserveStatusRemoteDataSource,
) {
    suspend fun getReserveStatusList(
        page: Int,
        size: Int,
    ): Result<ReserveStatusModel> {
        return kotlin.runCatching {
            reserveStatusRemoteDataSource.getReserveStatusList(page, size)
                .toReserveStatusModel()
        }
    }

    suspend fun getReserveStatusDetail(
        routeId: Int,
    ): Result<ReserveStatusDetailModel> {
        return kotlin.runCatching {
            reserveStatusRemoteDataSource.getReserveStatusDetail(routeId)
                .toReserveStatusDetailModel()
        }
    }

    suspend fun getMobileTicket(
        routeId: Int,
    ): Result<MobileTicketModel> {
        return kotlin.runCatching {
            reserveStatusRemoteDataSource.getMobileTicket(routeId)
                .toMobileTicketModel()
        }
    }

    suspend fun getSeatChart(
        routeId: Int,
    ): Result<SeatChartModel> {
        return kotlin.runCatching {
            reserveStatusRemoteDataSource.getSeatChart(routeId)
                .toSeatChartModel()
        }
    }
}
