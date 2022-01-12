package com.patofch.todoapp.data.data_source

import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE
import com.patofch.todoapp.data.data_source.model.TaskDtoEntity

@Dao
internal interface TaskDao {

    @Query("SELECT * FROM taskdtoentity")
    fun getTasks(): LiveData<List<TaskDtoEntity>>

    @Update(onConflict = REPLACE)
    suspend fun updateTask(task: TaskDtoEntity)

    @Insert(onConflict = REPLACE)
    suspend fun insertTask(task: TaskDtoEntity)

    @Delete
    suspend fun deleteTask(task: TaskDtoEntity)
}