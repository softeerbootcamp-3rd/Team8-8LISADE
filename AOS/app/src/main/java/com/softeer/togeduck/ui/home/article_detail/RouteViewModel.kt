package com.softeer.togeduck.ui.home.article_detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.softeer.togeduck.data.dto.request.home.ArticleRouteRequest
import com.softeer.togeduck.data.model.home.article_detail.RouteListModel
import com.softeer.togeduck.data.repository.ArticleDetailRepository
import com.softeer.togeduck.utils.DATA_LOAD_ERROR_MESSAGE
import com.softeer.togeduck.utils.recordErrLog
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class RouteViewModel @Inject constructor(
    private val articleDetailRepository: ArticleDetailRepository,
) : ViewModel() {

    private val tag = this.javaClass.simpleName.substring(0, 4)

    private var _errMessage = MutableLiveData<String>()
    val errMessage: LiveData<String> = _errMessage

    private val _articleRouteList = MutableLiveData<List<RouteListModel>>()
    val articleRouteList: LiveData<List<RouteListModel>> = _articleRouteList

    private var articleId = 1


    // Need FIx
    private val params = ArticleRouteRequest(
        city = "",
        sort = "",
        page = 1,
        size = 3,
    ).toMap()

    fun getArticleId(id:Int){
        articleId = id
    }

    private var _size = MutableLiveData("0")
    val size: LiveData<String> = _size
    fun setItemSize(itemCount: String) {
        _size.value = itemCount
    }

    fun getArticleRouteList() {
        viewModelScope.launch {
            articleDetailRepository.getArticleRoute(articleId.toString(), params)
                .onSuccess {
                    _articleRouteList.value = it
                }
                .onFailure {
                    recordErrLog(tag, it.message!!)
                    _errMessage.value = DATA_LOAD_ERROR_MESSAGE
                }
        }

    }
}