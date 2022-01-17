package com.patofch.todoapp.data.data_source

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.patofch.todoapp.data.data_source.model.CategoryDtoEntity
import kotlinx.coroutines.flow.Flow

@Dao
internal interface CategoryDao {

    @Query("SELECT * FROM category")
    fun getCategories(): Flow<List<CategoryDtoEntity>>

    @Insert(onConflict = REPLACE)
    suspend fun insertCategory(categoryDtoEntity: CategoryDtoEntity)

    @Delete
    suspend fun deleteCategory(categoryDtoEntity: CategoryDtoEntity)

}