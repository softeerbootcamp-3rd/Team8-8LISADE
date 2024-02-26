package com.softeer.togeduck.data.dto.response.reserve_status.reserve_detail

data class SeatChartResponse(
    val seatNo: Int,
    val totalSeats: Int,
    val row: Int,
    val column: Int,
    val backSeat: Int,
)