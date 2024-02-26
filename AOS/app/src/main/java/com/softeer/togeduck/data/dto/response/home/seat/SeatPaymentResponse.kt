package com.softeer.togeduck.data.dto.response.home.seat

data class SeatPaymentResponse(
    val festivalName: String,
    val startedAt: String,
    val sourceCity: String,
    val source: String,
    val departureAt: String,
    val arrivalAt: String,
    val destinationCity: String,
    val destination: String,
    val numberOfSeats: Int,
    val numberOfReservedSeats: Int,
    val price: Int,
)
