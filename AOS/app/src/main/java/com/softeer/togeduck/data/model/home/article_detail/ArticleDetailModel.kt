package com.softeer.togeduck.data.model.home.article_detail

data class ArticleDetailModel(
    val category: String,
    val content: String,
    val id: Int,
    val location: String,
    val paths: List<String>,
    val startedAt: String,
    val title: String
)