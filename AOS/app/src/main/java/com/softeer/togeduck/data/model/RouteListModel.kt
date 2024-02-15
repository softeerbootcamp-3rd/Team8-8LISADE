package com.softeer.togeduck.data.model

data class RouteListModel(
    val startDate: String,
    val place: String,
    val price: String,
    val totalPeople: Int,
    val currentPeople: Int,
    val currentType: String,
)
