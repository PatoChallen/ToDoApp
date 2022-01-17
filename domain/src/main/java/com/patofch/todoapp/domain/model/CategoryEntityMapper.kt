package com.patofch.todoapp.domain.model

interface CategoryEntityMapper<Entity>  {

    fun mapToCategory(entity: Entity): Category

    fun mapToCategoryDtoEntity(category: Category): Entity
}