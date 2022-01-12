package com.patofch.todoapp.data.data_source.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
internal data class SubTaskDtoEntity(
    @PrimaryKey val id: Int? = null,
    val taskId: Int? = null,
    val name: String,
    val description: String,
    val status: String? = null
)