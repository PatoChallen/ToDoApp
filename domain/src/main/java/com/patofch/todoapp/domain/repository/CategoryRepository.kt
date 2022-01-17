package com.patofch.todoapp.domain.repository

import com.patofch.todoapp.domain.model.category.Category
import kotlinx.coroutines.flow.Flow

interface CategoryRepository {

    fun getCategories(): Flow<List<Category>>

    suspend fun insertCategory(category: Category)

    suspend fun deleteCategory(category: Category)
}