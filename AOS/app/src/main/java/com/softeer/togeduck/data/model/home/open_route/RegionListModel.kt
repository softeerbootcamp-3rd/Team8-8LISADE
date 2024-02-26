package com.softeer.togeduck.data.model.home.open_route


data class RegionListModel(
    val id: Int,
    val region : String,
    val detailList: List<RegionDetailModel>,
)
