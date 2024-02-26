package com.softeer.togeduck.data.remote.datasource

import android.util.Log
import com.softeer.togeduck.data.dto.response.home.HomeCategoryFestivalResponse
import com.softeer.togeduck.data.dto.response.home.HomeCategoryResponse
import com.softeer.togeduck.data.dto.response.home.HomePopularArticleResponse
import com.softeer.togeduck.data.remote.service.HomeService
import javax.inject.Inject

class HomeRemoteDataSource @Inject constructor(
    private val homeService: HomeService,
) {
    suspend fun getCategory(): HomeCategoryResponse {
        return homeService.getCategory()
    }

    suspend fun getPopularArticle(): HomePopularArticleResponse {
        return homeService.getPopularArticle()
    }

    suspend fun getCategoryFestival(params: Map<String, String>): HomeCategoryFestivalResponse{
        return homeService.getCategoryFestival(params)
    }

}