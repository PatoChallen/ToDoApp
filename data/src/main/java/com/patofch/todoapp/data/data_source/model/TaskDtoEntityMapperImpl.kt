package com.patofch.todoapp.data.data_source.model

import com.patofch.todoapp.domain.model.Task
import com.patofch.todoapp.domain.model.TaskEntityMapper

internal class TaskDtoEntityMapperImpl : TaskEntityMapper<TaskDtoEntity> {

    override fun mapToTask(entity: TaskDtoEntity): Task {
        return entity.run {
            Task(
                id = id,
                parentId = parentId,
                name = name,
                description = description,
                status = status
            )
        }
    }

    override fun mapToTaskDtoEntity(task: Task): TaskDtoEntity {
        return task.run {
            TaskDtoEntity(
                id = id,
                parentId = parentId,
                name = name,
                description = description,
                status = status
            )
        }
    }
}