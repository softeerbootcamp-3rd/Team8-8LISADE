package com.softeer.togeduck.data.dto.response.home.seat

data class SeatsInfoResponse(
    val row: Int,
    val column: Int,
    val backSeat: Int,
    val totalSeats: Int,
    val reservedSeats: Int,
    val sourceCity: String,
    val source: String,
    val destinationCity: String,
    val destination: String,
    val price: Int,
    val seats: List<SeatStatusResponse>,
)

data class SeatStatusResponse(
    val id: Int,
    val seatNo: String,
    val status: SeatStatus,
)

enum class SeatStatus(val isAvailable: Boolean) {
    AVAILABLE(true), RESERVATION(false)
}