package com.softeer.togeduck.data.model.reserve_status.reserve_detail

data class MobileTicketModel(
    val title: String,
    val startedAt: String,
    val departurePlace: String,
    val arrivalPlace: String,
    val departureTime: String,
    val arrivalTime: String,
    val seatNo: String,
)