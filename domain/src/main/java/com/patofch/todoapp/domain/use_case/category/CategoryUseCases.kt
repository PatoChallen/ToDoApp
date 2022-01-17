package com.patofch.todoapp.domain.use_case.category

import javax.inject.Inject

data class CategoryUseCases @Inject constructor(
    val deleteCategory: DeleteCategory,
    val getCategories: GetCategories,
    val insertCategory: InsertCategory
)