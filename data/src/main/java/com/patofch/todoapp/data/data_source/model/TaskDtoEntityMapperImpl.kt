package com.patofch.todoapp.data.data_source.model

import com.patofch.todoapp.domain.model.task.Task
import com.patofch.todoapp.domain.model.task.TaskEntityMapper
import com.patofch.todoapp.domain.utils.toDate
import com.patofch.todoapp.domain.utils.toDateString
import javax.inject.Inject

internal class TaskDtoEntityMapperImpl @Inject constructor() : TaskEntityMapper<TaskDtoEntity> {

    override fun mapToTask(entity: TaskDtoEntity): Task {
        return entity.run {
            Task(
                id = id,
                parentId = parentId,
                name = name,
                description = description,
                categoryId = categoryId,
                creationDate = creationDate.toDate(),
                limitDate = limitDate?.toDate(),
                status = Task.TaskStatus.valueOf(status)
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
                categoryId = categoryId,
                creationDate = creationDate.toDateString(),
                limitDate = limitDate?.toDateString(),
                status = status.name
            )
        }
    }
}