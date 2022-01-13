package com.patofch.todoapp.data.data_source.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tasks")
internal data class TaskDtoEntity(
    @PrimaryKey val id: Int? = null,
    val parentId: Int? = null,
    val name: String,
    val description: String,
    val status: String? = null,
    val createdAt: String? = null
)