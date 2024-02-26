package com.softeer.togeduck.data.dto.response.reserve_status.reserve_detail

data class MobileTicketResponse(
    val title: String,
    val startedAt: String,
    val sourceCity: String,
    val source: String,
    val destinationCity: String,
    val destination: String,
    val departureAt: String,
    val arrivalAt: String,
    val seatNo: String,
)
