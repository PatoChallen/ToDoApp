package com.patofch.todoapp.domain.model.category

interface CategoryEntityMapper<Entity>  {

    fun mapToCategory(entity: Entity): Category

    fun mapToCategoryDtoEntity(category: Category): Entity
}