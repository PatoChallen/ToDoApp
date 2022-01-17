package com.patofch.todoapp.domain.use_case.task

import com.patofch.todoapp.domain.model.task.Task
import com.patofch.todoapp.domain.repository.TaskRepository
import kotlinx.coroutines.flow.Flow

class GetTasks (
    private val taskRepository: TaskRepository
) {

    operator fun invoke(): Flow<List<Task>> = taskRepository.getTasks()
}