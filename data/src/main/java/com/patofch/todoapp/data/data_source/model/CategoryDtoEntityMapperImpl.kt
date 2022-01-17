package com.patofch.todoapp.data.data_source.model

import com.patofch.todoapp.domain.model.category.Category
import com.patofch.todoapp.domain.model.category.CategoryEntityMapper
import javax.inject.Inject

internal class CategoryDtoEntityMapperImpl @Inject constructor() : CategoryEntityMapper<CategoryDtoEntity> {

    override fun mapToCategory(entity: CategoryDtoEntity): Category {
        return entity.run {
            Category(
                id = id,
                name = name,
                color = color.toULong()
            )
        }
    }

    override fun mapToCategoryDtoEntity(category: Category): CategoryDtoEntity {
        return category.run {
            CategoryDtoEntity(
                id = id,
                name = name,
                color = color.toString()
            )
        }
    }
}