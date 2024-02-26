package com.softeer.togeduck.data.mapper

import com.softeer.togeduck.data.dto.response.home.HomeCategoryFestivalResponse
import com.softeer.togeduck.data.dto.response.home.HomeCategoryResponse
import com.softeer.togeduck.data.dto.response.home.HomePopularArticleResponse
import com.softeer.togeduck.data.model.home.main.HomeArticleModel
import com.softeer.togeduck.data.model.home.main.HomeCategoryModel
import com.softeer.togeduck.data.model.home.main.PopularArticleModel


fun HomeCategoryResponse.toHomeCategoryModel(): List<HomeCategoryModel> {
    return this.categories.map { category ->
        HomeCategoryModel(
            imageUrl = category.image,
            title = category.name,
        )
    }
}

fun HomePopularArticleResponse.toPopularArticleModel(): List<PopularArticleModel>{
    return this.banner.map{banner->
        PopularArticleModel(
            id = banner.id,
            imageUrl = banner.thumbnailPath,
            concertDate = banner.startedAt,
            concertTitle = banner.title,
            concertArrival = banner.location
        )
    }
}


fun HomeCategoryFestivalResponse.toHomeArticleModel(): List<HomeArticleModel> {
    return this.content.map { content ->
        HomeArticleModel(
            id = content.id,
            imgUrl = content.thumbnailPath,
            title = content.title,
            place = content.location,
            startDate = content.startedAt,
            endDate = content.startedAt,
        )
    }
}
