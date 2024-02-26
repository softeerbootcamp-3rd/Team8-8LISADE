package com.softeer.togeduck.data.mapper

import com.softeer.togeduck.data.dto.response.article_detail.ArticleDetailResponse
import com.softeer.togeduck.data.dto.response.article_detail.ArticleRouteResponse
import com.softeer.togeduck.data.model.home.article_detail.ArticleDetailModel
import com.softeer.togeduck.data.model.home.article_detail.RouteListModel
import com.softeer.togeduck.data.model.home.main.HomeCategoryModel
import com.softeer.togeduck.utils.addCommas

fun ArticleDetailResponse.toArticleDetailModel(): ArticleDetailModel {
    return ArticleDetailModel(
        id = id,
        category = category,
        content = content,
        location = location,
        paths = paths,
        startedAt = startedAt,
        title = title,
    )
}

//val startDate: String,
//val place: String,
//val price: String,
//val totalPeople: Int,
//val currentPeople: Int,
//val currentType: String,
//val status: String,

fun ArticleRouteResponse.toRouteListModel(): List<RouteListModel>{
    return this.content.map { data ->
        RouteListModel(
            startDate = data.startedAt,
            place = data.station,
            price = data.price.addCommas(),
            totalPeople = data.totalSeats,
            currentPeople = data.reservedSeats,
            currentType = data.status,
        )
    }
}