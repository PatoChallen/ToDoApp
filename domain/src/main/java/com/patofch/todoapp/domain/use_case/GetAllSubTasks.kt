package com.patofch.todoapp.domain.use_case

import androidx.lifecycle.LiveData
import com.patofch.todoapp.domain.model.ToDoSubTask
import com.patofch.todoapp.domain.repository.ToDoRepository

class GetAllSubTasks (
    private val toDoRepository: ToDoRepository
) {

    operator fun invoke(taskId: Int): LiveData<List<ToDoSubTask>> = toDoRepository.getAllSubTasks(taskId)
}