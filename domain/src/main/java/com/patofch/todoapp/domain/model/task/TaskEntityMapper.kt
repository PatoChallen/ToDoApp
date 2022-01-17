package com.patofch.todoapp.domain.model.task

interface TaskEntityMapper<Entity>  {

    fun mapToTask(entity: Entity): Task

    fun mapToTaskDtoEntity(task: Task): Entity
}