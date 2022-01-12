package com.patofch.todoapp.domain.model

data class Task(
    val id: Int? = null,
    val parentId: Int? = null,
    val name: String,
    val description: String,
    val subTasks: List<Task> = emptyList(),
    val status: String? = null
)