package com.patofch.todoapp.domain.use_case

import com.patofch.todoapp.domain.model.Task
import com.patofch.todoapp.domain.repository.ToDoRepository

class DeleteTask(
    private val toDoRepository: ToDoRepository
) {

    suspend operator fun invoke(task: Task) = toDoRepository.deleteTask(task)
}