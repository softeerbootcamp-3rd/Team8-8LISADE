package com.softeer.togeduck.data.dto.request.home

class ArticleRouteRequest(
    val city: String,
    val page: Int,
    val size: Int,
    val sort: String,
) {
    fun toMap(): Map<String, String> {
        return mapOf(
            "city" to city,
            "page" to page.toString(),
            "size" to size.toString(),
            "sort" to sort,
        ).filterValues { it != null }.mapValues { it.value!! }
    }
}