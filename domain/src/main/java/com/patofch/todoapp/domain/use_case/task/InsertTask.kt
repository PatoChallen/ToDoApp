package com.patofch.todoapp.domain.use_case.task

import com.patofch.todoapp.domain.model.task.Task
import com.patofch.todoapp.domain.repository.TaskRepository

class InsertTask(
    private val taskRepository: TaskRepository
) {

    suspend operator fun invoke(task: Task) = taskRepository.insertTask(task)
}