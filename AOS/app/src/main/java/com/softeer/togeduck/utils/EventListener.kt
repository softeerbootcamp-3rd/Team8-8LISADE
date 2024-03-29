package com.softeer.togeduck.utils

import android.view.View
import com.softeer.togeduck.data.model.home.open_route.RegionDetailModel

interface ItemClick {
    fun onClick(view: View, position: Int)

}

interface ItemClickWithRouteId {
    fun onClick(view: View, position: Int, routeId: Int)

}

interface ItemClickWithCategoryId {
    fun onClick(view: View, position: Int, categoryId: Int)
}

interface ItemClickWithData {
    fun onClick(view: View, position: Int, detailList: List<RegionDetailModel>)
}