package com.softeer.togeduck.data.remote.datasource

import com.softeer.togeduck.data.dto.response.open_route.RegionListResponse
import com.softeer.togeduck.data.mapper.toRegionListModel
import com.softeer.togeduck.data.remote.service.RegionListService
import javax.inject.Inject

class RegionListRemoteDataSource @Inject constructor(
    private val regionListService:RegionListService,
) {
    suspend fun getRegionList():RegionListResponse{
        return regionListService.getRegionList()
    }
}
