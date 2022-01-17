package com.patofch.todoapp.domain.use_case.task

import javax.inject.Inject

data class TaskUseCases @Inject constructor(
    val deleteTask: DeleteTask,
    val getTasks: GetTasks,
    val insertTask: InsertTask,
    val updateTask: UpdateTask
)