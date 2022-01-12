package com.patofch.todoapp.domain.use_case

import androidx.lifecycle.LiveData
import com.patofch.todoapp.domain.model.Task
import com.patofch.todoapp.domain.repository.ToDoRepository

class GetTasks (
    private val toDoRepository: ToDoRepository
) {

    operator fun invoke(): LiveData<List<Task>> = toDoRepository.getTasks()
}