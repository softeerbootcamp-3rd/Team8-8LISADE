package com.softeer.togeduck.data.model.home.seat

data class SeatsInfoModel(
    val seatsCntPerRow: Int,
    val totalRows: Int,
    val backSeatsCnt: Int,
    val totalSeats: Int,
    val remainingSeats: Int,
    val departurePlace: String,
    val arrivalPlace: String,
    val price: Int,
    val formattedPrice: String,
    val seats: List<Int>,
)