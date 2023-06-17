package com.comunidadedevspace.taskbeats

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.comunidadedevspace.taskbeats.data.remote.NewsDto
import com.comunidadedevspace.taskbeats.data.remote.NewsResponse
import com.comunidadedevspace.taskbeats.data.remote.NewsService
import com.comunidadedevspace.taskbeats.presentation.NewsListViewModel
import kotlinx.coroutines.runBlocking
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class NewsListViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val service: NewsService = mock()
    private lateinit var underTest: NewsListViewModel

    @Test
    fun `GIVEN request succeed news WHEN fetch THEN return list`() {
        runBlocking {
            // GIVEN
            val expected = listOf(
                NewsDto(
                    id = "id1",
                    content = "content1",
                    imageUrl = "image1",
                    title = "title1"
                )
            )

            val response = NewsResponse(data = expected, category = "tech")
            whenever(service.fetchNews()).thenReturn(response)

            // WHEN
            underTest = NewsListViewModel(service)
            val result = underTest.newsListLiveData.getOrAwaitValue()

            // THEN
            assert(result == expected)
        }

    }
}