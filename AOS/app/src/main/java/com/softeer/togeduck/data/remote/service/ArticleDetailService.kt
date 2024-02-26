package com.softeer.togeduck.data.remote.service

import com.softeer.togeduck.data.dto.response.article_detail.ArticleDetailResponse
import com.softeer.togeduck.data.dto.response.article_detail.ArticleRouteResponse
import com.softeer.togeduck.data.model.home.article_detail.RouteListModel
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.QueryMap


interface ArticleDetailService {
    @GET("festivals/{id}")
    suspend fun getArticleDetail(@Path("id") id: String): ArticleDetailResponse

    @GET("festivals/{id}/routes")
    suspend fun getArticleRoute(
        @Path("id") id: String,
        @QueryMap queryParameters: Map<String, String>,
    ): ArticleRouteResponse
}