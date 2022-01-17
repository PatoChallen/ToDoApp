package com.patofch.todoapp.domain.use_case.category

import com.patofch.todoapp.domain.model.category.Category
import com.patofch.todoapp.domain.repository.CategoryRepository

class DeleteCategory(
    private val categoryRepository: CategoryRepository
) {

    suspend operator fun invoke(category: Category) = categoryRepository.deleteCategory(category)
}