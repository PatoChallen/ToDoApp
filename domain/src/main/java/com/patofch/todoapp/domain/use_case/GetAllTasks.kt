package com.patofch.todoapp.domain.use_case

import androidx.lifecycle.LiveData
import com.patofch.todoapp.domain.model.ToDoTask
import com.patofch.todoapp.domain.repository.ToDoRepository

class GetAllTasks (
    private val toDoRepository: ToDoRepository
) {

    operator fun invoke(): LiveData<List<ToDoTask>> = toDoRepository.getAllTasks()
}