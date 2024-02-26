package com.softeer.togeduck.data.repository

import com.softeer.togeduck.data.dto.request.home.seat.MySeatRequest
import com.softeer.togeduck.data.mapper.toMySeatRequest
import com.softeer.togeduck.data.mapper.toSeatInfoModel
import com.softeer.togeduck.data.mapper.toSeatPaymentModel
import com.softeer.togeduck.data.model.home.seat.MySeatModel
import com.softeer.togeduck.data.model.home.seat.SeatPaymentModel
import com.softeer.togeduck.data.model.home.seat.SeatsInfoModel
import com.softeer.togeduck.data.remote.datasource.SeatRemoteDataSource
import javax.inject.Inject

class SeatRepository @Inject constructor(private val seatRemoteDataSource: SeatRemoteDataSource) {

    suspend fun getSeatsInfo(
        routeId: Int,
    ): Result<SeatsInfoModel> {
        return kotlin.runCatching {
            seatRemoteDataSource.getSeatsInfo(routeId).toSeatInfoModel()
        }
    }

    suspend fun getSeatPayment(
        routeId: Int,
        seatNo: Int,
    ): Result<SeatPaymentModel> {
        return kotlin.runCatching {
            seatRemoteDataSource.getSeatPayment(routeId).toSeatPaymentModel(seatNo)
        }
    }

    suspend fun setMySeat(
        routeId: Int,
        mySeatModel: MySeatModel,
    ): Result<Any> {
        return kotlin.runCatching {
            seatRemoteDataSource.setMySeat(routeId, mySeatModel.toMySeatRequest())
        }
    }
}