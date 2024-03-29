package com.softeer.togeduck.data.mapper

import com.softeer.togeduck.data.dto.response.article_detail.ArticleDetailResponse
import com.softeer.togeduck.data.dto.response.article_detail.ArticleRouteResponse
import com.softeer.togeduck.data.dto.response.article_detail.RouteDetailResponse
import com.softeer.togeduck.data.model.home.article_detail.ArticleDetailModel
import com.softeer.togeduck.data.model.home.article_detail.RouteDetailModel
import com.softeer.togeduck.data.model.home.article_detail.RouteListModel
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

fun ArticleRouteResponse.toRouteListModel(): List<RouteListModel> {
    return this.content.map { data ->
        RouteListModel(
            routeId = data.id,
            startDate = data.startedAt,
            place = data.station,
            price = data.price.addCommas(),
            totalPeople = data.totalSeats,
            currentPeople = data.reservedSeats,
            currentType = data.status,
        )
    }
}

fun RouteDetailResponse.toRouteDetailModel(): RouteDetailModel {
    return RouteDetailModel(
        arrivalAt = arrivalAt,
        cost = cost.addCommas(),
        destination = destination,
        expectedAt = expectedAt,
        id = id,
        reservedSeats = reservedSeats,
        source = source,
        startedAt = startedAt,
        totalSeats = totalSeats,
    )
}