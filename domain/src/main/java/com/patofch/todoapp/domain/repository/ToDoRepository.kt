package com.patofch.todoapp.domain.repository

import androidx.lifecycle.LiveData
import com.patofch.todoapp.domain.model.ToDoSubTask
import com.patofch.todoapp.domain.model.ToDoTask

interface ToDoRepository {

    fun getAllTasks(): LiveData<List<ToDoTask>>

    fun getAllSubTasks(taskId: Int): LiveData<List<ToDoSubTask>>

    fun insertTask(toDoTask: ToDoTask)

    fun deleteTask(toDoTask: ToDoTask)

    fun insertSubTask(toDoSubTask: ToDoSubTask)

    fun deleteSubTask(toDoSubTask: ToDoSubTask)

}