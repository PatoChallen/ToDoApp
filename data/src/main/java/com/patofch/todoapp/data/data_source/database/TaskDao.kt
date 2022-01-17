package com.patofch.todoapp.data.data_source.database

import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE
import com.patofch.todoapp.data.data_source.model.TaskDtoEntity
import kotlinx.coroutines.flow.Flow

@Dao
internal interface TaskDao {

    @Query("SELECT * FROM tasks WHERE parentId is null")
    fun getTasks(): Flow<List<TaskDtoEntity>>

    @Query("SELECT * FROM tasks WHERE parentId = :parentId")
    fun getSubTasks(parentId: Int): Flow<List<TaskDtoEntity>>

    @Update(onConflict = REPLACE)
    suspend fun updateTask(task: TaskDtoEntity)

    @Insert(onConflict = REPLACE)
    suspend fun insertTask(task: TaskDtoEntity)

    @Delete
    suspend fun deleteTask(task: TaskDtoEntity)
}