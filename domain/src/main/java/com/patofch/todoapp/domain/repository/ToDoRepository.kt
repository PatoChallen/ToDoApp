package com.patofch.todoapp.domain.repository

import androidx.lifecycle.LiveData
import com.patofch.todoapp.domain.model.SubTask
import com.patofch.todoapp.domain.model.Task

interface ToDoRepository {

    fun getTasks(): LiveData<List<Task>>

    suspend fun updateTask(task: Task)

    suspend fun insertTask(task: Task)

    suspend fun deleteTask(task: Task)
}