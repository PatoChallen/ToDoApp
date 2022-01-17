package com.patofch.todoapp.data.data_source

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.patofch.todoapp.data.data_source.model.CategoryDtoEntity
import com.patofch.todoapp.data.data_source.model.TaskDtoEntity

@Database(
    entities = [
        TaskDtoEntity::class,
        CategoryDtoEntity::class
    ],
    version = 1
)
@TypeConverters()
internal abstract class ToDoDatabase: RoomDatabase() {

    abstract val taskDao: TaskDao

    abstract val categoryDao: CategoryDao

    companion object {
        const val DATABASE_NAME = "todoapp_db"
    }
}