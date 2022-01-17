package com.patofch.todoapp.data.repository

import com.patofch.todoapp.data.data_source.database.CategoryDao
import com.patofch.todoapp.data.data_source.model.CategoryDtoEntity
import com.patofch.todoapp.domain.model.category.Category
import com.patofch.todoapp.domain.model.category.CategoryEntityMapper
import com.patofch.todoapp.domain.repository.CategoryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

internal class CategoryRepositoryImpl @Inject constructor(
    private val categoryDao: CategoryDao,
    private val mapper: CategoryEntityMapper<CategoryDtoEntity>
) : CategoryRepository {
    override fun getCategories(): Flow<List<Category>> {
        return categoryDao.getCategories()
            .map { categoryList ->
            categoryList.map { mapper.mapToCategory(it) }
        }
    }

    override suspend fun insertCategory(category: Category) {
        categoryDao.insertCategory(
            mapper.mapToCategoryDtoEntity(category)
        )
    }

    override suspend fun deleteCategory(category: Category) {
        categoryDao.deleteCategory(
            mapper.mapToCategoryDtoEntity(category)
        )
    }
}