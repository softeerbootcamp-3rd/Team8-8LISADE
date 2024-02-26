package com.softeer.togeduck.data.dto.response.article_detail

data class ArticleDetailResponse(
    val category: String,
    val content: String,
    val id: Int,
    val location: String,
    val paths: List<String>,
    val startedAt: String,
    val title: String
)