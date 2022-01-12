package com.patofch.todoapp.domain.model

interface TaskEntityMapper<Entity>  {

    fun mapToTask(entity: Entity): Task

    fun mapToTaskDtoEntity(task: Task): Entity
}