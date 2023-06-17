package com.comunidadedevspace.taskbeats.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.comunidadedevspace.taskbeats.data.remote.NewsDto
import com.comunidadedevspace.taskbeats.data.remote.NewsService
import com.comunidadedevspace.taskbeats.data.remote.RetrofitModule
import kotlinx.coroutines.launch
import java.lang.Exception

class NewsListViewModel(
    private val newsService: NewsService
) : ViewModel() {


    private val _newsListLiveData = MutableLiveData<List<NewsDto>>()
    val newsListLiveData: LiveData<List<NewsDto>> = _newsListLiveData

    init {
        getNewsList()
    }

    private fun getNewsList() {
        viewModelScope.launch {
            try {
                val response = newsService.fetchNews()
                _newsListLiveData.value = response.data
            } catch (ex: Exception) {
                ex.printStackTrace()
            }
        }
    }

    companion object {

        fun create(): NewsListViewModel {
            val newsService = RetrofitModule.createNewsService()
            return NewsListViewModel(newsService)
        }
    }
}