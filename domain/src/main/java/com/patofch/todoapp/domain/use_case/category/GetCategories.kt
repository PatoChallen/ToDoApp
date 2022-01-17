package com.patofch.todoapp.domain.use_case.category

import com.patofch.todoapp.domain.model.category.Category
import com.patofch.todoapp.domain.repository.CategoryRepository
import kotlinx.coroutines.flow.Flow

class GetCategories (
    private val categoryRepository: CategoryRepository
) {

    operator fun invoke(): Flow<List<Category>> = categoryRepository.getCategories()
}