package com.comunidadedevspace.taskbeats

import androidx.lifecycle.MutableLiveData
import com.comunidadedevspace.taskbeats.data.Task
import com.comunidadedevspace.taskbeats.data.TaskDao
import com.comunidadedevspace.taskbeats.presentation.ActionType
import com.comunidadedevspace.taskbeats.presentation.TaskAction
import com.comunidadedevspace.taskbeats.presentation.TaskListViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@OptIn(ExperimentalCoroutinesApi::class)
class TaskListViewModelTest {

    private val taskDao: TaskDao = mock()
    private lateinit var underTest: TaskListViewModel

    private val testDispatcher = UnconfinedTestDispatcher()

    private val task = Task(
        id = 0,
        title = "title",
        description = "description"
    )
    private val tasks = listOf(task)

    @Before
    fun setup() {
        whenever(taskDao.getAll()).thenReturn(MutableLiveData(tasks))
        underTest = TaskListViewModel(taskDao, testDispatcher)
    }

    @Test
    fun get_all() {
        val listResult: List<Task> = underTest.taskListLiveData.value ?: emptyList()
        assert(listResult.isNotEmpty())
        assert(listResult.first() == task)
    }

    @Test
    fun test_delete_all() = runTest {
        // Given
        val taskAction = TaskAction(
            task = null,
            actionType = ActionType.DELETE_ALL.name
        )

        //When
        underTest.execute(taskAction)

        //Then
        verify(taskDao).deleteAll()
    }

    @Test
    fun test_update() = runTest {
        // Given
        val task = Task(
            id = 0,
            title = "Title",
            description = "Description"
        )
        val taskAction = TaskAction(
            task = task,
            actionType = ActionType.UPDATE.name
        )

        //When
        underTest.execute(taskAction)

        //Then
        verify(taskDao).update(task)
    }

    @Test
    fun test_create() = runTest {
        // Given
        val task = Task(
            id = 0,
            title = "Title",
            description = "Description"
        )
        val taskAction = TaskAction(
            task = task,
            actionType = ActionType.CREATE.name
        )

        //When
        underTest.execute(taskAction)

        //Then
        verify(taskDao).insert(task)
    }

    @Test
    fun test_delete_by_id() = runTest {
        // Given
        val task = Task(
            id = 0,
            title = "Title",
            description = "Description"
        )
        val taskAction = TaskAction(
            task = task,
            actionType = ActionType.DELETE.name
        )

        //When
        underTest.execute(taskAction)

        //Then
        verify(taskDao).deleteById(task.id)
    }

}