package com.softeer.togeduck.data.dto.response.reserve_status.reserve_detail

import com.squareup.moshi.Json

data class ReserveStatusDetailResponse(
    val festivalId: Int,
    val title: String,
    val seatNo: Int,
    val expectedDuration: String,
    val status: ReserveStatus,
    val totalSeats: Int,
    val reservedSeats: Int,
    val price: Int,
    val imagePath: String,
    val startedAt: String,
    val arrivedAt: String,
    val stationName: String,
    val stationCity: String,
    val festivalLocation: String,
    val festivalCity: String,
    val driverInfo: DriverInfoResponse,
)

enum class ReserveStatus(val value: String) {
    RECRUIT("모집중"),
    RECRUIT_COMPLETE("모집완료"),
    OPERATION_CONFIRM("출발확정"),
    OPERATION_COMPLETE("운행완료"),
}

data class DriverInfoResponse(
    @Json(name = "id")
    val id: Int,
    @Json(name = "name")
    val name: String,
    @Json(name = "company")
    val company: String,
    @Json(name = "phoneNumber")
    val phoneNumber: String,
    @Json(name = "carNumber")
    val carNumber: String,
    @Json(name = "imagePath")
    val imagePath: String,
)