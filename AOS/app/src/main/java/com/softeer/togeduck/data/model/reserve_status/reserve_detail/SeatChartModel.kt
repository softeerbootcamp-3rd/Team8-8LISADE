package com.softeer.togeduck.data.model.reserve_status.reserve_detail

data class SeatChartModel(
    val totalSeats: Int,
    val seatsCntPerRow: Int,
    val backSeatsCnt: Int,
    val totalRows: Int,
    val mySeatNo: Int,
)