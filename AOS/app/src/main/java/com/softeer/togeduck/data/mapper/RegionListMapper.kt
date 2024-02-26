package com.softeer.togeduck.data.mapper

import com.softeer.togeduck.data.dto.response.open_route.RegionListResponse
import com.softeer.togeduck.data.model.home.open_route.RegionDetailModel
import com.softeer.togeduck.data.model.home.open_route.RegionListModel


fun RegionListResponse.toRegionListModel():List<RegionListModel>{
    return this.locations.map{ data->
        RegionListModel(
            id = data.id,
            region = data.city,
            detailList = data.stations.map{
                RegionDetailModel( id = it.id, detail = it.station)
            }
        )

    }
}