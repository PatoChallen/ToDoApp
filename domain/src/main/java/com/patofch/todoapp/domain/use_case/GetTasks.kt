package com.patofch.todoapp.domain.use_case

import com.patofch.todoapp.domain.model.Task
import com.patofch.todoapp.domain.repository.ToDoRepository
import kotlinx.coroutines.flow.Flow

class GetTasks (
    private val toDoRepository: ToDoRepository
) {

    operator fun invoke(): Flow<List<Task>> = toDoRepository.getTasks()
}