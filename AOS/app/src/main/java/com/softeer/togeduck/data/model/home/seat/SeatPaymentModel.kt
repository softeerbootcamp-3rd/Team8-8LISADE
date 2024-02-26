package com.softeer.togeduck.data.model.home.seat

data class SeatPaymentModel(
    val festivalName: String,
    val festivalAt: String,
    val departurePlace: String,
    val departureAt: String,
    val arrivalPlace: String,
    val arrivalAt: String,
    val totalSeats: Int,
    val reservedSeats: Int,
    val recruitStatus: String,
    val mySeatNum: Int,
    val price: Int,
    val formattedPrice: String,
)