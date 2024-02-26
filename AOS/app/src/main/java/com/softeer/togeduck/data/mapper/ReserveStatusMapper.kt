package com.softeer.togeduck.data.mapper

import com.softeer.togeduck.data.dto.response.reserve_status.reserve_detail.ReserveStatusDetailResponse
import com.softeer.togeduck.data.dto.response.reserve_status.ReserveStatusResponse
import com.softeer.togeduck.data.dto.response.reserve_status.reserve_detail.MobileTicketResponse
import com.softeer.togeduck.data.dto.response.reserve_status.reserve_detail.ReserveStatus
import com.softeer.togeduck.data.dto.response.reserve_status.reserve_detail.SeatChartResponse
import com.softeer.togeduck.data.model.reserve_status.ReserveStatusItemModel
import com.softeer.togeduck.data.model.reserve_status.ReserveStatusModel
import com.softeer.togeduck.data.model.reserve_status.reserve_detail.DriverInfoModel
import com.softeer.togeduck.data.model.reserve_status.reserve_detail.MobileTicketModel
import com.softeer.togeduck.data.model.reserve_status.reserve_detail.ReserveStatusDetailModel
import com.softeer.togeduck.data.model.reserve_status.reserve_detail.SeatChartModel
import com.softeer.togeduck.utils.addCommas

fun ReserveStatusResponse.toReserveStatusModel(): ReserveStatusModel {
    return ReserveStatusModel(this.content.map {
        ReserveStatusItemModel(
            id = it.id,
            festivalName = it.title,
            festivalImg = it.imagePath,
            departureTime = it.startedAt,
            departurePlace = it.stationName,
            festivalPlace = it.location,
            cost = it.price,
            formattedCost = it.price.addCommas(),
            recruitStatus = it.totalSeats == it.reservedSeats,
            recruitPhrase = "${it.status.value}(${it.reservedSeats}/${it.totalSeats})",
        )
    })
}

fun ReserveStatusDetailResponse.toReserveStatusDetailModel(): ReserveStatusDetailModel {
    val driverDispatched = when (status) {
        ReserveStatus.RECRUIT, ReserveStatus.RECRUIT_COMPLETE -> false
        else -> true
    }
    return ReserveStatusDetailModel(
        festivalId = festivalId,
        title = title,
        seatNo = seatNo,
        expectedDuration = expectedDuration,
        status = status,
        recruitStatus = "${reservedSeats}/${totalSeats}",
        price = price,
        formattedPrice = price.addCommas(),
        imagePath = imagePath,
        departureTime = startedAt,
        arrivalTime = arrivedAt,
        departurePlace = "[${stationName}] ${stationCity}",
        arrivalPlace = "[${festivalCity}] ${festivalLocation}",
        driverDispatched = driverDispatched,
        driverInfo = DriverInfoModel(
            id = driverInfo.id,
            name = driverInfo.name,
            company = driverInfo.company,
            phoneNumber = driverInfo.phoneNumber,
            carNumber = driverInfo.carNumber,
            imagePath = driverInfo.imagePath,
        )
    )
}

fun MobileTicketResponse.toMobileTicketModel(): MobileTicketModel {
    return MobileTicketModel(
        title = title,
        startedAt = startedAt,
        departurePlace = "${source}(${sourceCity})",
        arrivalPlace = "${destination}(${destinationCity})",
        departureTime = departureAt,
        arrivalTime = arrivalAt,
        seatNo = seatNo,
    )
}

fun SeatChartResponse.toSeatChartModel(): SeatChartModel {
    return SeatChartModel(
        mySeatNo = seatNo,
        totalSeats = totalSeats,
        seatsCntPerRow = row,
        totalRows = column,
        backSeatsCnt = backSeat,
    )
}