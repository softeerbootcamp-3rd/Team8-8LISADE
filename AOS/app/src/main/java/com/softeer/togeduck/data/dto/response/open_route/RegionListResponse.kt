package com.softeer.togeduck.data.dto.response.open_route

data class RegionListResponse(
    val locations: List<Location>,
    val numberOfCities: Int
)

data class Location(
    val city: String,
    val id: Int,
    val stations: List<Station>
)

data class Station(
    val id: Int,
    val station: String
)