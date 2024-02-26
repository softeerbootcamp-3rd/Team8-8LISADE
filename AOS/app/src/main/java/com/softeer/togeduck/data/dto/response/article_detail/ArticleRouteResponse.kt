package com.softeer.togeduck.data.dto.response.article_detail

data class ArticleRouteResponse(
    val content: List<Content>,
    val empty: Boolean,
    val first: Boolean,
    val last: Boolean,
    val number: Int,
    val numberOfElements: Int,
    val pageable: Pageable,
    val size: Int,
    val sort: SortX
)

data class Content(
    val id: Int,
    val price: Int,
    val reservedSeats: Int,
    val startedAt: String,
    val station: String,
    val status: String,
    val totalSeats: Int
)

data class Pageable(
    val offset: Int,
    val pageNumber: Int,
    val pageSize: Int,
    val paged: Boolean,
    val sort: SortX,
    val unpaged: Boolean
)

data class SortX(
    val empty: Boolean,
    val sorted: Boolean,
    val unsorted: Boolean
)