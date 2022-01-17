package com.patofch.todoapp.data.repository

import com.patofch.todoapp.data.data_source.CategoryDao
import com.patofch.todoapp.data.data_source.TaskDao
import com.patofch.todoapp.data.data_source.model.CategoryDtoEntity
import com.patofch.todoapp.data.data_source.model.TaskDtoEntity
import com.patofch.todoapp.domain.model.CategoryEntityMapper
import com.patofch.todoapp.domain.model.Task
import com.patofch.todoapp.domain.model.TaskEntityMapper
import com.patofch.todoapp.domain.repository.ToDoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import javax.inject.Inject

internal class ToDoRepositoryImpl @Inject constructor(
    private val taskDao: TaskDao,
    private val categoryDao: CategoryDao,
    private val taskEntityMapper: TaskEntityMapper<TaskDtoEntity>,
    private val categoryEntityMapper: CategoryEntityMapper<CategoryDtoEntity>
) : ToDoRepository {

    override fun getTasks(): Flow<List<Task>> = taskDao.getTasks()
        .map { taskList ->
            taskList.map { taskEntityMapper.mapToTask(it) }
                .onEach { task ->
                    task.subTasks.addAll(
                        taskDao.getSubTasks(task.id!!).map { subTaskList ->
                            subTaskList.map { taskEntityMapper.mapToTask(it) }
                        }.firstOrNull() ?: emptyList()
                    )
                }
        }

    override suspend fun updateTask(task: Task) = taskDao.updateTask(taskEntityMapper.mapToTaskDtoEntity(task)).also {
        task.subTasks.forEach { insertTask(it) }
    }

    override suspend fun insertTask(task: Task) = taskDao.insertTask(taskEntityMapper.mapToTaskDtoEntity(task))

    override suspend fun deleteTask(task: Task) = taskDao.deleteTask(taskEntityMapper.mapToTaskDtoEntity(task)).also {
        task.subTasks.forEach { taskDao.deleteTask(taskEntityMapper.mapToTaskDtoEntity(it)) }
    }
}