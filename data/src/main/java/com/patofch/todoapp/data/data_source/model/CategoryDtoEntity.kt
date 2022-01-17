package com.patofch.todoapp.data.data_source.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "category")
class CategoryDtoEntity (
    @PrimaryKey val id: Int? = null,
    val name: String,
    val color: String
)
