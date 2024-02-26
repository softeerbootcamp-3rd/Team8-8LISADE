package com.softeer.togeduck.ui.home.article_detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.softeer.togeduck.data.model.home.article_detail.ArticleDetailModel
import com.softeer.togeduck.data.repository.ArticleDetailRepository
import com.softeer.togeduck.utils.DATA_LOAD_ERROR_MESSAGE
import com.softeer.togeduck.utils.recordErrLog
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ArticleDetailViewModel @Inject constructor(
    private val articleDetailRepository: ArticleDetailRepository
): ViewModel() {

    private val tag = this.javaClass.simpleName.substring(0, 4)

    private var _errMessage = MutableLiveData<String>()
    val errMessage: LiveData<String> = _errMessage

    private val _articleDetail = MutableLiveData<ArticleDetailModel>()
    val articleDetail: LiveData<ArticleDetailModel> = _articleDetail
    private var articleId = 1

    fun getArticleId(id:Int){
        articleId = id
    }

    fun getArticleDetails(){
        viewModelScope.launch {
            articleDetailRepository.getFestivalDetail(articleId.toString())
                .onSuccess {
                    _articleDetail.value = it
                }
                .onFailure {
                    recordErrLog(tag, it.message!!)
                    _errMessage.value = DATA_LOAD_ERROR_MESSAGE
                }
        }
    }


}