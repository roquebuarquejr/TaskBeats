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
            val expectedTop = listOf(
                NewsDto(
                    id = "id1",
                    content = "content1",
                    imageUrl = "image1",
                    title = "title1"
                )
            )

            val expectedAll = listOf(
                NewsDto(
                    id = "id2",
                    content = "content2",
                    imageUrl = "image2",
                    title = "title2"
                )
            )

            val topResponse = NewsResponse(data = expectedTop)
            val allResponse = NewsResponse(data = expectedAll)
            whenever(service.fetchTopNews()).thenReturn(topResponse)
            whenever(service.fetchAllNews()).thenReturn(allResponse)

            // WHEN
            underTest = NewsListViewModel(service)
            val result = underTest.newsListLiveData.getOrAwaitValue()

            // THEN
            assert(result == expectedTop + expectedAll)

        }

    }
}