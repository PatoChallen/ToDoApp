package com.patofch.todoapp.domain.repository

import com.patofch.todoapp.domain.model.Task
import kotlinx.coroutines.flow.Flow

interface ToDoRepository {

    fun getTasks(): Flow<List<Task>>

    suspend fun updateTask(task: Task)

    suspend fun insertTask(task: Task)

    suspend fun deleteTask(task: Task)
}