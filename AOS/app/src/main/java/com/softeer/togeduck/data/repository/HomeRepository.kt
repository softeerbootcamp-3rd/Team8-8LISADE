package com.softeer.togeduck.data.repository

import com.softeer.togeduck.data.mapper.toHomeArticleModel
import com.softeer.togeduck.data.mapper.toHomeCategoryModel
import com.softeer.togeduck.data.mapper.toPopularArticleModel
import com.softeer.togeduck.data.model.home.main.HomeArticleModel
import com.softeer.togeduck.data.model.home.main.HomeCategoryModel
import com.softeer.togeduck.data.model.home.main.PopularArticleModel
import com.softeer.togeduck.data.remote.datasource.HomeRemoteDataSource
import javax.inject.Inject

class HomeRepository @Inject constructor(
    private val homeRemoteDataSource: HomeRemoteDataSource,
) {

    suspend fun getCategory():Result<List<HomeCategoryModel>>{
        return kotlin.runCatching {
            homeRemoteDataSource.getCategory().toHomeCategoryModel()
        }
    }

    suspend fun getPopularArticle():Result<List<PopularArticleModel>>{
        return kotlin.runCatching {
            homeRemoteDataSource.getPopularArticle().toPopularArticleModel()
        }
    }

    suspend fun getCategoryFestival(params: Map<String, String>):Result<List<HomeArticleModel>>{
        return kotlin.runCatching {
            homeRemoteDataSource.getCategoryFestival(params).toHomeArticleModel()
        }
    }

}