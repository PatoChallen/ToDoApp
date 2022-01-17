package com.patofch.todoapp.data.data_source.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tasks")
internal data class TaskDtoEntity(
    @PrimaryKey val id: Int? = null,
    val parentId: Int? = null,
    val name: String,
    val description: String? = null,
    val categoryId: Int? = null,
    val creationDate: String,
    val limitDate: String? = null,
    val status: String
)