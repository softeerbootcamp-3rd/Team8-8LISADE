package com.softeer.togeduck.data.remote.service

import com.softeer.togeduck.data.dto.BaseResponse
import com.softeer.togeduck.data.dto.response.home.HomeCategoryFestivalResponse
import com.softeer.togeduck.data.dto.response.home.HomeCategoryResponse
import com.softeer.togeduck.data.dto.response.home.HomePopularArticleResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.QueryMap

interface HomeService {
    @GET("category")
    suspend fun getCategory():HomeCategoryResponse

    @GET("festivals/best")
    suspend fun getPopularArticle():HomePopularArticleResponse

    @GET("festivals")
    suspend fun getCategoryFestival(
        @QueryMap queryParameters: Map<String, String>,
    ):HomeCategoryFestivalResponse
}