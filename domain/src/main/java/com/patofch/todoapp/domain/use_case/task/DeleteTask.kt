package com.patofch.todoapp.domain.use_case.task

import com.patofch.todoapp.domain.model.task.Task
import com.patofch.todoapp.domain.repository.TaskRepository

class DeleteTask(
    private val taskRepository: TaskRepository
) {

    suspend operator fun invoke(task: Task) = taskRepository.deleteTask(task)
}