package com.patofch.todoapp.data.repository

import com.patofch.todoapp.data.data_source.TaskDao
import com.patofch.todoapp.data.data_source.model.TaskDtoEntity
import com.patofch.todoapp.domain.model.Task
import com.patofch.todoapp.domain.model.TaskEntityMapper
import com.patofch.todoapp.domain.repository.ToDoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import javax.inject.Inject

internal class ToDoRepositoryImpl @Inject constructor(
    private val taskDao: TaskDao,
    private val mapper: TaskEntityMapper<TaskDtoEntity>
) : ToDoRepository {

    override fun getTasks(): Flow<List<Task>> = taskDao.getTasks()
        .map { taskList ->
            taskList.map { mapper.mapToTask(it) }
                .onEach { task ->
                    task.subTasks.addAll(
                        taskDao.getSubTasks(task.id!!).map { subTaskList ->
                            subTaskList.map { mapper.mapToTask(it) }
                        }.firstOrNull() ?: emptyList()
                    )
                }
        }

    override suspend fun updateTask(task: Task) = taskDao.updateTask(mapper.mapToTaskDtoEntity(task)).also {
        task.subTasks.forEach { insertTask(it) }
    }

    override suspend fun insertTask(task: Task) = taskDao.insertTask(mapper.mapToTaskDtoEntity(task))

    override suspend fun deleteTask(task: Task) = taskDao.deleteTask(mapper.mapToTaskDtoEntity(task)).also {
        task.subTasks.forEach { taskDao.deleteTask(mapper.mapToTaskDtoEntity(it)) }
    }
}