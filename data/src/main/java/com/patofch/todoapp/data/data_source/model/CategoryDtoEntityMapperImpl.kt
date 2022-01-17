package com.patofch.todoapp.data.data_source.model

import com.patofch.todoapp.domain.model.Category
import com.patofch.todoapp.domain.model.CategoryEntityMapper

internal class CategoryDtoEntityMapperImpl : CategoryEntityMapper<CategoryDtoEntity> {

    override fun mapToCategory(entity: CategoryDtoEntity): Category {
        return entity.run {
            Category(
                id = id,
                name = name,
                color = color
            )
        }
    }

    override fun mapToCategoryDtoEntity(category: Category): CategoryDtoEntity {
        return category.run {
            CategoryDtoEntity(
                id = id,
                name = name,
                color = color
            )
        }
    }
}