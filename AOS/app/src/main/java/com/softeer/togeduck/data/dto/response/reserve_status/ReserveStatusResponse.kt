package com.softeer.togeduck.data.dto.response.reserve_status

import com.softeer.togeduck.data.dto.response.reserve_status.reserve_detail.ReserveStatus
import com.squareup.moshi.Json

data class ReserveStatusResponse(
    val content: List<ReserveStatusItemResponse>,
    val pageable: Pageable,
    val first: Boolean,
    val last: Boolean,
    val size: Int,
    val number: Int,
    val sort: Sort,
    val numberOfElements: Int,
    val empty: Boolean,
)

data class ReserveStatusItemResponse(
    val id: Int,
    val title: String,
    val startedAt: String,
    val location: String,
    val stationName: String,
    val price: Int,
    val status: ReserveStatus,
    val totalSeats: Int,
    val reservedSeats: Int,
    val imagePath: String,
)

data class Pageable(
    @Json(name = "pageNumber")
    val pageNumber: Int,
    @Json(name = "pageSize")
    val pageSize: Int,
    @Json(name = "sort")
    val sort: Sort,
    @Json(name = "offset")
    val offset: Int,
    @Json(name = "paged")
    val paged: Boolean,
    @Json(name = "unpaged")
    val unpaged: Boolean,
)

data class Sort(
    @Json(name = "empty")
    val empty: Boolean,
    @Json(name = "unsorted")
    val unsorted: Boolean,
    @Json(name = "sorted")
    val sorted: Boolean,
)