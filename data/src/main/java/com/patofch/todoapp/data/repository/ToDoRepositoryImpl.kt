package com.patofch.todoapp.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.patofch.todoapp.data.data_source.TaskDao
import com.patofch.todoapp.data.data_source.model.TaskDtoEntityMapperImpl
import com.patofch.todoapp.domain.model.Task
import com.patofch.todoapp.domain.repository.ToDoRepository
import javax.inject.Inject

internal class ToDoRepositoryImpl @Inject constructor(
    private val taskDao: TaskDao,
    private val mapper: TaskDtoEntityMapperImpl
) : ToDoRepository {

    override fun getTasks(): LiveData<List<Task>> = Transformations.map(
        taskDao.getTasks()
    ) { taskList ->
        taskList.map {
            mapper.mapToTask(it)
        }
    }

    override suspend fun updateTask(task: Task) = taskDao.updateTask(mapper.mapToTaskDtoEntity(task))

    override suspend fun insertTask(task: Task) = taskDao.insertTask(mapper.mapToTaskDtoEntity(task))

    override suspend fun deleteTask(task: Task) = taskDao.deleteTask(mapper.mapToTaskDtoEntity(task))
}