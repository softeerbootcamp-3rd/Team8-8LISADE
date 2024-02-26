package com.softeer.togeduck.data.repository

import com.softeer.togeduck.data.mapper.toArticleDetailModel
import com.softeer.togeduck.data.mapper.toRouteListModel
import com.softeer.togeduck.data.model.home.article_detail.ArticleDetailModel
import com.softeer.togeduck.data.model.home.article_detail.RouteListModel
import com.softeer.togeduck.data.remote.datasource.ArticleDetailRemoteDataSource
import javax.inject.Inject

class ArticleDetailRepository @Inject constructor(
    private val articleDetailRemoteDataSource: ArticleDetailRemoteDataSource,
) {

    suspend fun getFestivalDetail(
        id:String,
    ):Result<ArticleDetailModel>{
        return kotlin.runCatching {
            articleDetailRemoteDataSource.getArticleDetail(id).toArticleDetailModel()
        }
    }

    suspend fun getArticleRoute(
        id:String,
        params: Map<String, String>
    ): Result<List<RouteListModel>>{
        return kotlin.runCatching {
            articleDetailRemoteDataSource.getArticleRoute(id,params).toRouteListModel()
        }
    }

}