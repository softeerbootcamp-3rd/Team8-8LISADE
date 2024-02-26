package com.softeer.togeduck.data.mapper

import com.softeer.togeduck.data.dto.request.home.seat.MySeatRequest
import com.softeer.togeduck.data.dto.response.home.seat.SeatPaymentResponse
import com.softeer.togeduck.data.dto.response.home.seat.SeatsInfoResponse
import com.softeer.togeduck.data.model.home.seat.MySeatModel
import com.softeer.togeduck.data.model.home.seat.SeatPaymentModel
import com.softeer.togeduck.data.model.home.seat.SeatsInfoModel
import com.softeer.togeduck.utils.addCommas

fun SeatsInfoResponse.toSeatInfoModel(): SeatsInfoModel {
    return SeatsInfoModel(seatsCntPerRow = row,
        totalRows = column,
        backSeatsCnt = backSeat,
        totalSeats = totalSeats,
        remainingSeats = totalSeats - reservedSeats,
        departurePlace = "${sourceCity}(${source})",
        arrivalPlace = "${destinationCity}(${destination})",
        price = price,
        formattedPrice = price.addCommas(),
        seats = seats.filter { !it.status.isAvailable }.map { it.seatNo.toInt() })
}

fun SeatPaymentResponse.toSeatPaymentModel(seatNo: Int): SeatPaymentModel {
    return SeatPaymentModel(
        festivalName = festivalName,
        festivalAt = startedAt,
        departurePlace = "${sourceCity}(${source})",
        departureAt = departureAt,
        arrivalPlace = "${destinationCity}(${destination})",
        arrivalAt = arrivalAt,
        totalSeats = numberOfSeats,
        reservedSeats = numberOfReservedSeats,
        recruitStatus = "${numberOfReservedSeats} / ${numberOfSeats}",
        mySeatNum = seatNo,
        price = price,
        formattedPrice = price.addCommas()
    )
}

fun MySeatModel.toMySeatRequest(): MySeatRequest {
    return MySeatRequest(
        seatNo = seatNo
    )
}