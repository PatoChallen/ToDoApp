package com.patofch.todoapp.domain.model

data class ToDoTask(
    val id: Int? = null,
    val parentId: Int? = null,
    val name: String,
    val description: String,
    val subTasks: List<ToDoSubTask> = emptyList(),
    val status: String? = null
)