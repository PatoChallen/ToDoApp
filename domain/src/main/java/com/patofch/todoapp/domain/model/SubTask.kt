package com.patofch.todoapp.domain.model

data class SubTask(
    val id: Int? = null,
    val taskId: Int? = null,
    val name: String,
    val description: String,
    val status: String? = null
)