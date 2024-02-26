package com.softeer.togeduck.data.repository

import com.softeer.togeduck.data.mapper.toRegionListModel
import com.softeer.togeduck.data.model.home.open_route.RegionListModel
import com.softeer.togeduck.data.remote.datasource.RegionListRemoteDataSource
import javax.inject.Inject

class RegionListRepository @Inject constructor(
    private val regionListRemoteDataSource: RegionListRemoteDataSource
) {
    suspend fun getRegionList():Result<List<RegionListModel>>{
        return kotlin.runCatching {
            regionListRemoteDataSource.getRegionList().toRegionListModel()
        }

    }
}